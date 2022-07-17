package com.kenko.ceea.controller.dto;


import com.kenko.ceea.common.Gender;
import lombok.Data;

@Data
public class TeacherInfoDTO {
    private String id;
    private String username;
    private String name;
    private Gender gender;
    private String email;
    private String phone;
}
