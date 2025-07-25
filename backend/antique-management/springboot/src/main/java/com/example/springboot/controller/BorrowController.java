package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.AncientPageRequest;
import com.example.springboot.controller.request.BorrowPageRequest;
import com.example.springboot.entity.Ancient;
import com.example.springboot.entity.Borrow;
import com.example.springboot.service.IAncientService;
import com.example.springboot.service.IBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    IBorrowService borrowService;

    @PostMapping("/save")
    public Result save(@RequestBody Borrow obj){
        borrowService.save(obj);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Borrow obj = borrowService.getById(id);
        return Result.success(obj);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Borrow obj){
        borrowService.update(obj);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(){
        List<Borrow> list = borrowService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result page(BorrowPageRequest PageRequest){
        return Result.success(borrowService.page(PageRequest));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        borrowService.deleteById(id);
        return Result.success();
    }

    @PutMapping("/return/{id}")
    public Result returnBorrow(@PathVariable Integer id) {
        borrowService.returnById(id);
        return Result.success();
    }

    //@GetMapping("/lineCharts/{timeRange}")  // 注意方法类型和路径变量
    //public Result getLineChartData(@PathVariable String timeRange) {
    //    Map<String, Object> data = borrowService.getLineChartData(timeRange);
    //    return Result.success(data);
    //}
//
    //@GetMapping("/lineCharts/{timeRange}")
    //public Result lineCharts(@PathVariable String timeRange){
    //    return Result.success(borrowService.getCountByTimeRange(timeRange));
    //}

}

