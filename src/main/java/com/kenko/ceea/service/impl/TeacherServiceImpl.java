package com.kenko.ceea.service.impl;


import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenko.ceea.common.Constants;
import com.kenko.ceea.controller.vo.LoginVO;
import com.kenko.ceea.entity.Teacher;
import com.kenko.ceea.exception.ServiceException;
import com.kenko.ceea.mapper.TeacherMapper;
import com.kenko.ceea.service.ITeacherService;
import com.kenko.ceea.utils.TokenUtils;
import org.springframework.stereotype.Service;


@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    private static final Log log = Log.get();

    @Override
    public String login(LoginVO teacher) {
        Teacher one = getTeacherInfo(teacher);
        if (one != null) {
            return TokenUtils.generateToken(one.getId(), one.getPassword());
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public Boolean register(Teacher teacher) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", teacher.getUsername());
        try {
            Teacher one = getOne(queryWrapper);
            if (one != null) {
                throw new ServiceException(Constants.CODE_600, "用户名已存在");
            } else {
                return save(teacher);
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
    }

    private Teacher getTeacherInfo(LoginVO teacher) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", teacher.getUsername());
        queryWrapper.eq("password", teacher.getPassword());
        try {
            return getOne(queryWrapper);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
    }
}
