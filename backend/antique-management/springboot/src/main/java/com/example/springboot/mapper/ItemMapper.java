package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.ItemPageRequest;
import com.example.springboot.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ItemMapper {

    List<Item> list();


    void save(Item item);

    Item getById(Integer id);

    void updateById(Item item);

    void deleteById(Integer id);

    List<Item> listByCondition(@Param("name") String name, @Param("address") String address);

}