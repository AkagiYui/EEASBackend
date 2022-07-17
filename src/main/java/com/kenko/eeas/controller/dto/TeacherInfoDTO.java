package com.kenko.eeas.controller.dto;


import com.kenko.eeas.common.Gender;
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
