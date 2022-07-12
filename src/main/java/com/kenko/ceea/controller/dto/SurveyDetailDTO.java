package com.kenko.ceea.controller.dto;

import com.kenko.ceea.entity.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class SurveyDetailDTO {
    private String id;
    private String title;
    private String description;
    private String password;
    private List<QuestionType> questionTypes;
    private List<QuestionDTO> questions;
}

