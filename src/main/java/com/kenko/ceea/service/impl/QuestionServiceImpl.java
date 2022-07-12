package com.kenko.ceea.service.impl;


import cn.hutool.log.Log;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenko.ceea.entity.Question;
import com.kenko.ceea.mapper.QuestionMapper;
import com.kenko.ceea.service.IQuestionService;
import org.springframework.stereotype.Service;


@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    private static final Log log = Log.get();


}
