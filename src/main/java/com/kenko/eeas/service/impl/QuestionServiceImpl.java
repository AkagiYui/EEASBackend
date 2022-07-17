package com.kenko.eeas.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenko.eeas.entity.Question;
import com.kenko.eeas.mapper.QuestionMapper;
import com.kenko.eeas.service.IQuestionService;
import org.springframework.stereotype.Service;


@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {
}
