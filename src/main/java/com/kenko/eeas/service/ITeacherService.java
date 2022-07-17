package com.kenko.eeas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kenko.eeas.controller.vo.LoginVO;
import com.kenko.eeas.entity.Teacher;


public interface ITeacherService extends IService<Teacher> {
    Boolean register(Teacher teacher);

    Teacher getOneTeacher(LoginVO teacher);

    Boolean usernameExist(String username);
}
