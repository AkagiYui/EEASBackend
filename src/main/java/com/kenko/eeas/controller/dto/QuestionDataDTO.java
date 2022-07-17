package com.kenko.eeas.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDataDTO {
    private String id;
    private Integer number;
    private String content;
    private String questionTypeId;
    private List<AnswerDataDTO> answers;
}
