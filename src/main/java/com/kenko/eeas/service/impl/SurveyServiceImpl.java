package com.kenko.eeas.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenko.eeas.entity.Survey;
import com.kenko.eeas.mapper.SurveyMapper;
import com.kenko.eeas.service.ISurveyService;
import org.springframework.stereotype.Service;


@Service
public class SurveyServiceImpl extends ServiceImpl<SurveyMapper, Survey> implements ISurveyService {
}
