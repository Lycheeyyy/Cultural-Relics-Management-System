package com.example.springboot.controller.request;

import lombok.Data;

@Data
public class CategoryPageRequest extends BaseRequest{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
