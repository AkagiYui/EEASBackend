package com.kenko.ceea.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenko.ceea.entity.QuestionType;
import com.kenko.ceea.mapper.QuestionTypeMapper;
import com.kenko.ceea.service.IQuestionTypeService;
import org.springframework.stereotype.Service;


@Service
public class QuestionTypeServiceImpl extends ServiceImpl<QuestionTypeMapper, QuestionType> implements IQuestionTypeService {
}
