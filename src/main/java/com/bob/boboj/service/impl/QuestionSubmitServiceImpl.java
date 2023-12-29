package com.bob.boboj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bob.boboj.common.ErrorCode;
import com.bob.boboj.exception.BusinessException;
import com.bob.boboj.model.dto.questionSubmit.JudgeInfo;
import com.bob.boboj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.bob.boboj.model.entity.Question;
import com.bob.boboj.model.entity.QuestionSubmit;
import com.bob.boboj.model.entity.QuestionSubmit;
import com.bob.boboj.model.entity.User;
import com.bob.boboj.service.QuestionService;
import com.bob.boboj.service.QuestionSubmitService;
import com.bob.boboj.service.QuestionSubmitService;
import com.bob.boboj.mapper.QuestionSubmitMapper;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author SuperBob
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2023-12-29 15:48:36
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{
    @Resource
    private QuestionService questionService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        Long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        //todo 设置初始状态
        questionSubmit.setStatus(0);
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据插入失败！");
        }
        return questionSubmit.getId();
    }
}




