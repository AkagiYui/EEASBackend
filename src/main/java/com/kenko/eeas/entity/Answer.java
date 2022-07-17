package com.kenko.eeas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Answer {
    private String questionId;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String content;
}
