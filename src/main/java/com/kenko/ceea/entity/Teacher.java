package com.kenko.ceea.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kenko.ceea.common.Gender;
import lombok.Data;

@Data
public class Teacher {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String username;
    private String password;
    private String name;
    private Gender gender;
    private String email;
    private String phone;
    private Boolean isActive;
    private Boolean isAdmin;
}
