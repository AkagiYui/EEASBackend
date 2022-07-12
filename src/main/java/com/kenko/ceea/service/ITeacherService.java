package com.kenko.ceea.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kenko.ceea.controller.vo.LoginVO;
import com.kenko.ceea.entity.Teacher;


public interface ITeacherService extends IService<Teacher> {

    String login(LoginVO teacher);

    Boolean register(Teacher teacher);

}
