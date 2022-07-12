package com.kenko.ceea.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class QuestionVO {
    private String id;
    private List<String> answers; // 这里有可能是只有一个答案，也有可能是选择题的id
}
