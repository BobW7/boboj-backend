package com.bob.boboj.judge.strategy;

import com.bob.boboj.model.dto.questionSubmit.JudgeInfo;
import org.springframework.stereotype.Service;

/**
 * 判题策略管理，简化调用
 */
@Service
public class JudgeStrategyManager {
    /**
     * 判题
     *
     * @param judgeContext
     * @return
     */
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        String language = judgeContext.getQuestionSubmit().getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if (language.equals("java")) {
            judgeStrategy = new JavaJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
