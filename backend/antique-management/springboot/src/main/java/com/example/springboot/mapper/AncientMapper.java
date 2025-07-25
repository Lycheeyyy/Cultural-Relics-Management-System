package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Ancient;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AncientMapper {

    List<Ancient> list();

    List<Ancient> listByCondition(BaseRequest baseRequest);

    void save(Ancient obj);

    Ancient getById(Integer id);

    void updateById(Ancient obj);

    void deleteById(Integer id);

    Ancient getBynoNumber(String noNumber);

    void update(Ancient obj);


}