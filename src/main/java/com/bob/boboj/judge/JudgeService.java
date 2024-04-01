package com.bob.boboj.judge;

import org.springframework.stereotype.Service;

/**
 * 判题服务
 */
@Service
public interface JudgeService {

    /**
     * 判题
     *
     * @param questionSubmitId
     */
    void doJudge(long questionSubmitId);
}
