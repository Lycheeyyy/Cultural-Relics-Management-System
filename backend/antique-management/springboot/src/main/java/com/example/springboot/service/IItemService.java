package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.ItemPageRequest;
import com.example.springboot.entity.Item;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IItemService {

    List<Item> list();

    PageInfo<Item> page(BaseRequest baseRequest);

    void save(Item item);

    Item getById(Integer id);

    void update(Item item);

    void deleteById(Integer id);
}