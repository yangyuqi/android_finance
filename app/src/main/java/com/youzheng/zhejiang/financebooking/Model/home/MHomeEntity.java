package com.youzheng.zhejiang.financebooking.Model.home;

import java.util.List;

public class MHomeEntity {
    private String respCode ;
    private String respMsg ;
    private Integer count ;
    private List<MHomeDetailsEntity> data ;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<MHomeDetailsEntity> getData() {
        return data;
    }

    public void setData(List<MHomeDetailsEntity> data) {
        this.data = data;
    }
}
