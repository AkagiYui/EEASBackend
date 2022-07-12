package com.kenko.ceea.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class SurveySubmitVO {
    private String password;
    private List<QuestionVO> questions;
}

