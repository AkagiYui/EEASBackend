package com.kenko.ceea.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import com.kenko.ceea.common.Constants;
import com.kenko.ceea.common.Result;
import com.kenko.ceea.controller.dto.TeacherInfoDTO;
import com.kenko.ceea.controller.vo.LoginVO;
import com.kenko.ceea.entity.Teacher;
import com.kenko.ceea.service.ITeacherService;
import com.kenko.ceea.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private ITeacherService teacherService;

    // 登录，获取JWT
    @PostMapping("/login")
    public Result getUserJwt(@RequestBody LoginVO teacher) {
        String username = teacher.getUsername();
        String password = teacher.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        HMac hMac = SecureUtil.hmacSha1("kenko");
        String s = hMac.digestHex(teacher.getPassword());
        teacher.setPassword(s);
        String token = teacherService.login(teacher);
        return Result.success(token);
    }

    // 获取当前用户信息
    @GetMapping("/info")
    public Result getUserInfo() {
        Teacher currentTeacher = TokenUtils.getCurrentTeacher();
        TeacherInfoDTO teacherInfoDTO = new TeacherInfoDTO();
        if (currentTeacher == null) {
            return Result.error(Constants.CODE_401, "未登录");
        }
        BeanUtils.copyProperties(currentTeacher, teacherInfoDTO);
        return Result.success(teacherInfoDTO);
    }

    @Value("${spring.profiles.active}")
    private String isDev;

    // 临时增加测试用户，后期请看情况删除
    @PostMapping("/register")
    public Result addUser(@RequestBody Teacher teacher) {
        if (isDev.equals("prod")) {
            return Result.error(Constants.CODE_400, "生产环境该接口不可用");
        }
        HMac hMac = SecureUtil.hmacSha1("kenko");
        String s = hMac.digestHex(teacher.getPassword());
        teacher.setPassword(s);
        return Result.success(teacherService.register(teacher));
    }
}
