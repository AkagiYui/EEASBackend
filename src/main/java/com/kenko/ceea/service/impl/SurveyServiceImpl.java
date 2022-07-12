package com.kenko.ceea.service.impl;


import cn.hutool.log.Log;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenko.ceea.entity.Survey;
import com.kenko.ceea.mapper.SurveyMapper;
import com.kenko.ceea.service.ISurveyService;
import org.springframework.stereotype.Service;


@Service
public class SurveyServiceImpl extends ServiceImpl<SurveyMapper, Survey> implements ISurveyService {

    private static final Log log = Log.get();


}
