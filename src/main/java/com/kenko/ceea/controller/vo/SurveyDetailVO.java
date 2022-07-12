package com.kenko.ceea.controller.vo;

import com.kenko.ceea.controller.dto.QuestionDTO;
import lombok.Data;

import java.util.List;

@Data
public class SurveyDetailVO {
    private String title;
    private String description;
    private String password;
    private List<QuestionDTO> questions;
}

