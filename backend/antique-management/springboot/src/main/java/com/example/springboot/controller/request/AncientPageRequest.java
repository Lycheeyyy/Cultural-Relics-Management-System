package com.example.springboot.controller.request;

import lombok.Data;

@Data
public class AncientPageRequest extends BaseRequest{
    private String name;
    private String noNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoNumber() {
        return noNumber;
    }

    public void setNoNumber(String noNumber) {
        this.noNumber = noNumber;
    }
}
