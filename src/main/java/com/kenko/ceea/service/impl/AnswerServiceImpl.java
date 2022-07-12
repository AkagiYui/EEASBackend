package com.kenko.ceea.service.impl;


import cn.hutool.log.Log;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenko.ceea.entity.Answer;
import com.kenko.ceea.mapper.AnswerMapper;
import com.kenko.ceea.service.IAnswerService;
import org.springframework.stereotype.Service;


@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {

    private static final Log log = Log.get();


}
