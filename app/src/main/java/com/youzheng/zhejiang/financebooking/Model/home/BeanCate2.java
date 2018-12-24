package com.youzheng.zhejiang.financebooking.Model.home;

import java.io.Serializable;

public class BeanCate2 implements Serializable{
    private String type ;
    private Integer position ;
    private String name ;

    public BeanCate2(String type, Integer position, String name) {
        this.type = type;
        this.position = position;
        this.name = name;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
