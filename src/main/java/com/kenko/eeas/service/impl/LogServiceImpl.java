package com.kenko.eeas.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenko.eeas.common.LogLevel;
import com.kenko.eeas.entity.Log;
import com.kenko.eeas.mapper.LogMapper;
import com.kenko.eeas.service.ILogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {
    @Value("${log.save}")
    private boolean needSave;

    @Override
    public Boolean save(String content) {
        if (!needSave) {
            return true;
        }
        Log log = new Log();
        log.setContent(content);
        log.setLevel(-1);
        return this.save(log);
    }

    @Override
    public Boolean save(String content, LogLevel level) {
        if (!needSave) {
            return true;
        }
        Log log = new Log();
        log.setContent(content);
        log.setLevel(level.ordinal());
        return this.save(log);
    }
}
