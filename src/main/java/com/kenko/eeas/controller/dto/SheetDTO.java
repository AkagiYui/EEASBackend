package com.kenko.eeas.controller.dto;

import com.kenko.eeas.entity.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class SheetDTO {
    private String id;
    private String title;
    private String description;
    private List<QuestionType> questionTypes;
    private List<QuestionDTO> questions;
}
