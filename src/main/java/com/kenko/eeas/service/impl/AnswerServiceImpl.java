package com.kenko.eeas.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenko.eeas.entity.Answer;
import com.kenko.eeas.mapper.AnswerMapper;
import com.kenko.eeas.service.IAnswerService;
import org.springframework.stereotype.Service;


@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {
}
