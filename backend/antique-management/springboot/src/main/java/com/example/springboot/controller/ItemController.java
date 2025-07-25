package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.ItemPageRequest;
import com.example.springboot.entity.Item;
import com.example.springboot.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    IItemService itemService;

    @PostMapping("/save")
    public Result save(@RequestBody Item item){
        itemService.save(item);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Item item = itemService.getById(id);
        return Result.success(item);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Item item){
        itemService.update(item);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(){
        List<Item> items = itemService.list();
        return Result.success(items);
    }

    @GetMapping("/page")
    public Result page(ItemPageRequest itemPageRequest){
        return Result.success(itemService.page(itemPageRequest));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        itemService.deleteById(id);
        return Result.success();
    }
}