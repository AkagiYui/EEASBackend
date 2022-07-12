package com.kenko.ceea.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {
    private String id;
    private String type;
    private String content;
    private List<ChoiceDTO> selections;
}
