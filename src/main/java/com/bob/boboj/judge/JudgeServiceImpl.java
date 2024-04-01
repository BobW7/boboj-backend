package com.bob.boboj.judge;

import cn.hutool.json.JSONUtil;
import com.bob.boboj.common.ErrorCode;
import com.bob.boboj.exception.BusinessException;
import com.bob.boboj.judge.codesandbox.CodeSandBox;
import com.bob.boboj.judge.codesandbox.CodeSandBoxFactory;
import com.bob.boboj.judge.codesandbox.CodeSandBoxLogProxy;
import com.bob.boboj.judge.codesandbox.model.ExecuteCodeRequest;
import com.bob.boboj.judge.codesandbox.model.ExecuteCodeResponse;
import com.bob.boboj.judge.strategy.JudgeContext;
import com.bob.boboj.judge.strategy.JudgeStrategyManager;
import com.bob.boboj.model.dto.question.JudgeCase;
import com.bob.boboj.model.dto.questionSubmit.JudgeInfo;
import com.bob.boboj.model.entity.Question;
import com.bob.boboj.model.entity.QuestionSubmit;
import com.bob.boboj.model.enums.QuestionSubmitStatusEnum;
import com.bob.boboj.service.QuestionService;
import com.bob.boboj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {


    @Value("${codesandbox.type:example}")
    private String type;

    @Resource
    private JudgeStrategyManager judgeStrategyManager;
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;

    @Override
    public void doJudge(long questionSubmitId) {

        // 1、传入题目的提交id，获取对应的题目、提交信息（代码，编程语言等等）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交记录不存在！");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在！");
        }

        // 2、如果题目的提交状态不为等待中，就不重复执行了，直接返回
        Integer status = questionSubmit.getStatus();
        if (!status.equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目已判定完毕或正在判定！");
        }
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());

        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 3、调用沙箱，获取判题结果
        // 静态工厂指定沙箱类型
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        // 将沙箱对象传入代理增强，使其输出日志信息
        codeSandBox = new CodeSandBoxLogProxy(codeSandBox);
        // 从题目提交记录中得到判题语言和代码
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 从题目的judgeCase中得到输入用例，后端存的是JSON数组记得要转为List
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream()
                .map(JudgeCase::getInput)
                .collect(Collectors.toList());

        // 来自@Builder注解，链式调用，建造者模式
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        // 用增强后的代理类来调用execute方法
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();

        // 4、根据沙箱的判题结果，设置题目的状态和信息
        // 设置判题上下文对象，用于给判题策略提供信息
        JudgeContext judgeContext = new JudgeContext();
        // 将代码沙箱返回的响应值中的JudgeInfo赋给判题上下文
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo judgeInfo = judgeStrategyManager.doJudge(judgeContext);

        //修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        questionSubmitService.getById(questionId);
    }
}
