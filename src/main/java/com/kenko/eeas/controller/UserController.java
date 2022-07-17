package com.kenko.eeas.controller;

import com.kenko.eeas.common.HTTPCode;
import com.kenko.eeas.common.Result;
import com.kenko.eeas.controller.dto.TeacherInfoDTO;
import com.kenko.eeas.controller.vo.LoginVO;
import com.kenko.eeas.entity.Teacher;
import com.kenko.eeas.service.ITeacherService;
import com.kenko.eeas.utils.TokenUtils;
import com.kenko.eeas.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Tag(name = "user", description = "用户接口")
public class UserController {
    @Resource
    private ITeacherService teacherService;

    // 登录，获取JWT
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "获取 JWT token")
    public Result getUserJwt(@RequestBody LoginVO loginVO) {
        if (Utils.isUsernamePasswordBlank(loginVO)) {
            return Result.error(HTTPCode.PARAM_ERROR, "参数错误");
        }
        Utils.encryptPassword(loginVO);

        Teacher teacher = teacherService.getOneTeacher(loginVO);
        if (teacher == null) {
            return Result.error(HTTPCode.FORBIDDEN, "用户名或密码错误");
        }
        if (!teacher.getIsActive()) {
            return Result.error(HTTPCode.FORBIDDEN, "用户已被禁用");
        }
        String token = TokenUtils.generateToken(teacher.getId(), teacher.getPassword());
        return Result.success(token);
    }

    // 获取当前用户信息
    @GetMapping("/info")
    @Operation(summary = "获取用户数据")
    public Result getUserInfo() {
        Teacher currentTeacher = TokenUtils.getCurrentTeacher();
        if (currentTeacher == null) {
            return Result.error(HTTPCode.NOT_PERMIT, "未登录");
        }
        TeacherInfoDTO teacherInfoDTO = new TeacherInfoDTO();
        BeanUtils.copyProperties(currentTeacher, teacherInfoDTO);
        return Result.success(teacherInfoDTO);
    }

    // TODO: 新增用户
    @PostMapping("/add")
    @Operation(summary = "新增用户")
    public Result addUser(@RequestBody Teacher teacher) {
        if (Utils.isUsernamePasswordBlank(teacher)) {
            return Result.error(HTTPCode.PARAM_ERROR, "参数错误");
        }
        Utils.encryptPassword(teacher);
        return Result.success(teacherService.register(teacher));
    }
}
