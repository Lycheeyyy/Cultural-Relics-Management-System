package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Ancient;
import com.example.springboot.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IAncientService {
    List<Ancient> list();

    PageInfo<Ancient> page(BaseRequest baseRequest);

    void save(Ancient obj);

    Ancient getById(Integer id);

    void update(Ancient obj);

    void deleteById(Integer id);
}
