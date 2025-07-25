package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.AncientPageRequest;
import com.example.springboot.entity.Ancient;
import com.example.springboot.service.IAncientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/ancient")
public class AncientController {

    @Autowired
    IAncientService ancientService;

    @PostMapping("/save")
    public Result save(@RequestBody Ancient obj){
        ancientService.save(obj);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Ancient obj = ancientService.getById(id);
        return Result.success(obj);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Ancient obj){
        ancientService.update(obj);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(){
        List<Ancient> list = ancientService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result page(AncientPageRequest PageRequest){
        return Result.success(ancientService.page(PageRequest));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        ancientService.deleteById(id);
        return Result.success();
    }

}

