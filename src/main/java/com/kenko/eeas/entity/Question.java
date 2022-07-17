package com.kenko.eeas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Question {
    private String surveyId;
    private String questionTypeId;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private Integer number;
    private String content;
}
