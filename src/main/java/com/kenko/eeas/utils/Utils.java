package com.kenko.eeas.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import com.kenko.eeas.controller.vo.LoginVO;
import com.kenko.eeas.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class Utils {
    public static Boolean isUsernamePasswordBlank(Teacher teacher) {
        return teacher.getUsername().isBlank() || teacher.getPassword().isBlank();
    }

    public static Boolean isUsernamePasswordBlank(LoginVO loginVO) {
        return loginVO.getUsername().isBlank() || loginVO.getPassword().isBlank();
    }

    public static void encryptPassword(Teacher teacher) {
        HMac hMac = SecureUtil.hmacSha1("kenko");
        String s = hMac.digestHex(teacher.getPassword());
        teacher.setPassword(s);
    }

    public static void encryptPassword(LoginVO loginVO) {
        HMac hMac = SecureUtil.hmacSha1("kenko");
        String s = hMac.digestHex(loginVO.getPassword());
        loginVO.setPassword(s);
    }
}
