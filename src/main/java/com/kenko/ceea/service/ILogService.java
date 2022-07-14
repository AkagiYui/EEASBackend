package com.kenko.ceea.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kenko.ceea.common.LogLevel;
import com.kenko.ceea.entity.Log;

public interface ILogService extends IService<Log> {
    Boolean save(String content);

    Boolean save(String content, LogLevel level);
}
