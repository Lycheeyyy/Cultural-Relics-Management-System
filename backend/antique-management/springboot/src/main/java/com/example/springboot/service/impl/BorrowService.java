package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Ancient;
import com.example.springboot.entity.Borrow;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AncientMapper;
import com.example.springboot.mapper.BorrowMapper;
import com.example.springboot.service.IAncientService;
import com.example.springboot.service.IBorrowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BorrowService implements IBorrowService {

    @Resource
    BorrowMapper borrowMapper;
    @Autowired
    private AncientMapper ancientMapper;

    @Override
    public List<Borrow> list() {
        return borrowMapper.list();
    }

    @Override
    public PageInfo<Borrow> page(BaseRequest baseRequest){
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
        return new PageInfo<>(borrowMapper.listByCondition(baseRequest));
    }

    @Override
    public void save(Borrow obj) {
        String itemNo = obj.getItemNo();
        Ancient ancient = ancientMapper.getBynoNumber(itemNo);
        if(ancient.getState() .equals("已外借") ){
            throw new ServiceException("该文物藏品已外借");
        }else if(ancient.getState().equals ("修复中")){
            throw new ServiceException("该文物藏品正在修复中，不可外借");
        }else if(ancient.getState().equals("已展出") ){
            ancient.setState("已外借");
            ancientMapper.update(ancient);
        }else if(ancient.getState().equals("库存中") ){
            ancient.setState("已外借");
            ancientMapper.update(ancient);
        }
        obj.setCreatetime(new Date());
        borrowMapper.save(obj);
    }

    @Override
    public Borrow getById(Integer id) {
        return borrowMapper.getById(id);
    }

    @Override
    public void update(Borrow obj) {
        obj.setUpdatetime(new Date());
        borrowMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        borrowMapper.deleteById(id);
    }



    @Override
    public void returnById(Integer id) {
        // 1. 获取借阅记录
        Borrow borrow = borrowMapper.getById(id);
        if (borrow == null) {
            throw new ServiceException("借阅记录不存在");
        }

        // 2. 获取对应文物
        Ancient ancient = ancientMapper.getBynoNumber(borrow.getItemNo());
        if (ancient == null) {
            throw new ServiceException("对应文物不存在");
        }

        // 3. 更新文物状态为"库存中"
        ancient.setState("库存中");
        ancientMapper.update(ancient);

        // 4. 删除借阅记录
        borrowMapper.deleteById(id);

        log.info("文物 {} 已归还，借阅记录 {} 已删除", ancient.getNoNumber(), id);
    }

    //@Override
    //public Map<String, Object> getCountByTimeRange(String timeRange){
    //    Map<String, Object> map = new HashMap<>();
    //    Date today = new Date();
    //    List<DateTime> dateRange;
    //    switch (timeRange){
    //        case "week":
    //            dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today,-6),today, DateField.DAY_OF_WEEK);
    //            break;
    //        case "month":
    //            dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today,-29),today, DateField.DAY_OF_MONTH);
    //            break;
    //        case "month2":
    //            dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today,-59),today, DateField.DAY_OF_MONTH);
    //            break;
    //        case "month3":
    //            dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today,-89),today, DateField.DAY_OF_MONTH);
    //            break;
    //        default:
    //            dateRange = new ArrayList<>();
    //    }
    //    List<String> dateStrRange = datetimeToDateStr(dateRange);
    //    map.put("date",dateStrRange);
    //    List<BorrowReturCountPO> borrowCount = borrowMapper.getCountByTimeRange(timeRange,1);
//
    //    map.put("count",countList(borrowCount,dateStrRange));
//
    //    return map;
    //}
//
    //private List<String> datetimeToDateStr(List<DateTime> dateTimeList){
    //    List<String> list = CollUtil.newArrayList();                 
    //    if(CollUtil.isEmpty(dateTimeList)){
    //        return list;
    //    }
    //    for (DateTime dateTime : dateTimeList) {
    //        String date = DateUtil.formatDate(dateTime);
    //        list.add(date);
    //    }
    //    return list;
    //}
//
    //


}
