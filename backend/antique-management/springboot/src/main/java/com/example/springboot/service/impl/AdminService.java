package com.example.springboot.service.impl;

import java.sql.SQLException;
import java.util.logging.Logger;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Item;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.mapper.ItemMapper;
import com.example.springboot.service.IAdminService;
import com.example.springboot.service.IItemService;
import com.example.springboot.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminService {

    @Autowired
    AdminMapper adminMapper;

    private static final String DEFAULT_PASSWORD = "123";
    private static final String PASS_SALT = "lychee";

    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest){
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
        List<Admin> admins = adminMapper.listByCondition(baseRequest);
        return new PageInfo<>(admins);
    }

    @Override
    public void save(Admin obj){
        if(StrUtil.isBlank(obj.getPassword())){
            obj.setPassword(DEFAULT_PASSWORD);
        }
    obj.setPassword(securePass(obj.getPassword()));
        obj.setCreatetime(new Date());

        try{
            adminMapper.save(obj);
        }catch(Exception e){
            if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) {
                log.error("数据插入失败，username:{}",obj.getUsername());
                throw new ServiceException("用户名重复");
            }
        }
    }

    @Override
    public Admin getById(Integer id){
        return adminMapper.getById(id);
    }

    @Override
    public void update(Admin obj){
        obj.setUpdatetime(new Date());
        adminMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id){
        adminMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest request){
        request.setPassword(securePass(request.getPassword()));
        Admin admin = adminMapper.getByUsernameAndPassword(request.getUsername(), request.getPassword());
        if(admin==null){
            throw new ServiceException("用户名或密码错误");
        }
        if(!admin.isStatus()){
            throw new ServiceException("当前用户处于禁用状态，请联系管理员");
        }
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(admin,loginDTO);
        String token = TokenUtils.genToken(String.valueOf(admin.getId()),admin.getPassword());
        loginDTO.setToken(token);
        return loginDTO;
    }

    @Override
    public void changePass(PasswordRequest request){
        request.setNewPass(securePass(request.getNewPass()));
        int count = adminMapper.updatePassword(request);
        if(count <= 0){
            throw new ServiceException("修改密码失败");
        }
    }

    private String securePass(String password){
        return SecureUtil.md5(password + PASS_SALT);
    }
}