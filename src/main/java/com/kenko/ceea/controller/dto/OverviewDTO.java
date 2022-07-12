package com.kenko.ceea.controller.dto;

import lombok.Data;

@Data
public class OverviewDTO {
    private Long totalSurvey;  // 总问卷数
    private Long activeSurvey;  // 进行中问卷数
    private Long submitCount;  // 提交问卷数
}
