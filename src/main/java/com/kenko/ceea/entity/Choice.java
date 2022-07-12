package com.kenko.ceea.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Choice {
    private String questionId;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private Integer number;
    private String content;
    private Integer count;
}
