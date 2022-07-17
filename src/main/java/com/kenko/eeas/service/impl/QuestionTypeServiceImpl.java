package com.kenko.eeas.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenko.eeas.entity.QuestionType;
import com.kenko.eeas.mapper.QuestionTypeMapper;
import com.kenko.eeas.service.IQuestionTypeService;
import org.springframework.stereotype.Service;


@Service
public class QuestionTypeServiceImpl extends ServiceImpl<QuestionTypeMapper, QuestionType> implements IQuestionTypeService {
}
