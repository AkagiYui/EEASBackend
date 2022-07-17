package com.kenko.eeas.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenko.eeas.entity.Choice;
import com.kenko.eeas.mapper.ChoiceMapper;
import com.kenko.eeas.service.IChoiceService;
import org.springframework.stereotype.Service;


@Service
public class ChoiceServiceImpl extends ServiceImpl<ChoiceMapper, Choice> implements IChoiceService {
}
