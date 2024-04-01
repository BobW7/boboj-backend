package com.bob.boboj.judge.strategy;

import com.bob.boboj.model.dto.question.JudgeCase;
import com.bob.boboj.model.dto.questionSubmit.JudgeInfo;
import com.bob.boboj.model.entity.Question;
import lombok.Data;

import java.util.List;

/**
 * 用于定义在策略中传递的参数
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private List<JudgeCase> judgeCaseList;
    private Question question;

    public JudgeContext() {
    }
}
