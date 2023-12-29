package com.bob.boboj.service;

import com.bob.boboj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.bob.boboj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bob.boboj.model.entity.User;

/**
* @author SuperBob
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2023-12-29 15:48:36
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return 提交记录的id
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

}
