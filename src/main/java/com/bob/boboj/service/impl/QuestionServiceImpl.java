package com.bob.boboj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bob.boboj.model.entity.Question;
import com.bob.boboj.service.QuestionService;
import com.bob.boboj.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author SuperBob
* @description 针对表【question(帖子)】的数据库操作Service实现
* @createDate 2023-12-29 15:47:36
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




