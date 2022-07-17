package com.kenko.eeas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Log {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private Integer level;
    private String content;
    private LocalDateTime createTime;
}
