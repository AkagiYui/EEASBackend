package com.kenko.ceea.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenko.ceea.entity.Answer;
import com.kenko.ceea.mapper.AnswerMapper;
import com.kenko.ceea.service.IAnswerService;
import org.springframework.stereotype.Service;


@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {
}
