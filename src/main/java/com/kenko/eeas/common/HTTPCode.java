package com.kenko.eeas.common;

public interface HTTPCode {
    Integer SUCCESS = 200; // 成功
    Integer NOTHING_CHANGE = 304; // 不需改变
    Integer PARAM_ERROR = 400; // 参数错误
    Integer FORBIDDEN = 403; // 禁止请求
    Integer NOT_PERMIT = 401; // 权限不足
    Integer NOT_FOUND = 404; // 没有找到资源
    Integer SYSTEM_ERROR = 500; // 系统错误
    Integer OTHER_ERROR = 600; // 其他业务异常
}
