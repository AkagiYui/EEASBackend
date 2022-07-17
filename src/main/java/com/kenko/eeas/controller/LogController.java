package com.kenko.eeas.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kenko.eeas.common.Result;
import com.kenko.eeas.entity.Log;
import com.kenko.eeas.service.ILogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/log")
public class LogController {
    @Resource
    private ILogService logService;

    @GetMapping("/page/{pageNum}/{pageSize}")
    public Result getLogList(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        Page<Log> page = logService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }
}
