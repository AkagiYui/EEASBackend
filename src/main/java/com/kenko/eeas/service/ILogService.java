package com.kenko.eeas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kenko.eeas.common.LogLevel;
import com.kenko.eeas.entity.Log;

public interface ILogService extends IService<Log> {
    Boolean save(String content);

    Boolean save(String content, LogLevel level);
}
