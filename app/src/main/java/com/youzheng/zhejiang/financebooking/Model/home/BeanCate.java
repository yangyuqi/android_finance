package com.youzheng.zhejiang.financebooking.Model.home;

import java.io.Serializable;

public class BeanCate implements Serializable{
    private String type ;
    private Integer position ;

    public BeanCate(String type, Integer position) {
        this.type = type;
        this.position = position;
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
}
