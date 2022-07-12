package com.kenko.ceea.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class SurveyDataDTO {
    private String id;
    private String title;
    private String description;
    private Integer submitCount;
    private List<QuestionDataDTO> questions;
}
