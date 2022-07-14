package com.kenko.ceea.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Survey {
    private String teacherId;
    private String id;
    private String title;
    private String description;
    private String password;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isActive;
    private Boolean isDeleted;
    private LocalDateTime createTime;
    private Integer submitCount;
}
