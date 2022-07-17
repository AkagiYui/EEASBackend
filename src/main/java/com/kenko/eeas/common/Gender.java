package com.kenko.eeas.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女"),
    OTHER(3, "其它");

    Gender(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @EnumValue

    private final int code;

    @JsonValue
    private final String desc;
}
