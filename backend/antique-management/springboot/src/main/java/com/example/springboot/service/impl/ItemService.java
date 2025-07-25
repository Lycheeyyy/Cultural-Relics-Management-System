package com.example.springboot.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.ItemPageRequest;
import com.example.springboot.entity.Item;
import com.example.springboot.mapper.ItemMapper;
import com.example.springboot.service.IItemService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;

import java.util.Date;
import java.util.List;

@Service
public class ItemService implements IItemService {

    @Autowired
    ItemMapper itemMapper;

    @Override
    public List<Item> list() {
        return itemMapper.list();
    }

    @Override
    public PageInfo<Item> page(BaseRequest baseRequest){
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
        List<Item> items = itemMapper.listByCondition(baseRequest.getName(),baseRequest.getAddress());
        return new PageInfo<>(items);
    }

    @Override
    public void save(Item item){
        Date date = new Date();
        item.setItemName(DateUtil.format(date,"yyyyMMdd")+Math.abs(IdUtil.fastSimpleUUID().hashCode()));
        item.setCreatetime(date);
        itemMapper.save(item);
    }

    @Override
    public Item getById(Integer id){
        return itemMapper.getById(id);
    }

    @Override
    public void update(Item item){
        item.setUpdatetime(new Date());
        itemMapper.updateById(item);
    }

    @Override
    public void deleteById(Integer id){
        itemMapper.deleteById(id);
    }
}