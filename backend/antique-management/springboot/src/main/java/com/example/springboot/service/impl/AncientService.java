package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Ancient;
import com.example.springboot.mapper.AncientMapper;
import com.example.springboot.service.IAncientService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AncientService implements IAncientService {

    @Resource
    AncientMapper ancientMapper;

    @Override
    public List<Ancient> list() {
        return ancientMapper.list();
    }

    @Override
    public PageInfo<Ancient> page(BaseRequest baseRequest){
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
        return new PageInfo<>(ancientMapper.listByCondition(baseRequest));
    }

    @Override
    public void save(Ancient obj) {
        obj.setCategory(category(obj.getCategories()));
        obj.setCreatetime(new Date());
        ancientMapper.save(obj);
    }

    @Override
    public Ancient getById(Integer id) {
        return ancientMapper.getById(id);
    }

    @Override
    public void update(Ancient obj) {
        obj.setCategory(category(obj.getCategories()));
        obj.setUpdatetime(new Date());
        ancientMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        ancientMapper.deleteById(id);
    }



    private String category(List<String> categories){
        StringBuilder sb=new StringBuilder();
        if(CollUtil.isNotEmpty(categories)){
            categories.forEach(v ->sb.append(v).append(">"));
            return sb.substring(0,sb.lastIndexOf(">"));
        }
        return sb.toString();
    }
}
