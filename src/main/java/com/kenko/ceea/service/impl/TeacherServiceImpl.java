package com.kenko.ceea.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenko.ceea.common.HTTPCode;
import com.kenko.ceea.controller.vo.LoginVO;
import com.kenko.ceea.entity.Teacher;
import com.kenko.ceea.exception.ServiceException;
import com.kenko.ceea.mapper.TeacherMapper;
import com.kenko.ceea.service.ITeacherService;
import org.springframework.stereotype.Service;


@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Override
    public Boolean register(Teacher teacher) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", teacher.getUsername());
        try {
            Teacher one = getOne(queryWrapper);
            if (one != null) {
                throw new ServiceException(HTTPCode.OTHER_ERROR, "用户名已存在");
            } else {
                return save(teacher);
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(HTTPCode.SYSTEM_ERROR, "系统错误");
        }
    }

    @Override
    public Teacher getOneTeacher(LoginVO teacher) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", teacher.getUsername());
        queryWrapper.eq("password", teacher.getPassword());
        try {
            return getOne(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(HTTPCode.SYSTEM_ERROR, "系统错误");
        }
    }

    @Override
    public Boolean usernameExist(String username) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return count(queryWrapper) > 0;
    }
}
