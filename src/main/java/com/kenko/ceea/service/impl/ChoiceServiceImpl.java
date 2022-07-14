package com.kenko.ceea.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenko.ceea.entity.Choice;
import com.kenko.ceea.mapper.ChoiceMapper;
import com.kenko.ceea.service.IChoiceService;
import org.springframework.stereotype.Service;


@Service
public class ChoiceServiceImpl extends ServiceImpl<ChoiceMapper, Choice> implements IChoiceService {
}
