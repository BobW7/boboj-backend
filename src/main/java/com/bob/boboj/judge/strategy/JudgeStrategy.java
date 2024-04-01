package com.bob.boboj.judge.strategy;

import com.bob.boboj.model.dto.question.JudgeConfig;
import com.bob.boboj.model.dto.questionSubmit.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
