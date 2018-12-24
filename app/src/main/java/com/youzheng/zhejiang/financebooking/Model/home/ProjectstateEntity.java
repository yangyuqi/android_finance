package com.youzheng.zhejiang.financebooking.Model.home;

import java.util.List;

public class ProjectstateEntity {
    private String respCode ;
    private String respMsg ;
    private Integer count ;
    private List<ProjectstateDataEntity> data ;

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

    public List<ProjectstateDataEntity> getData() {
        return data;
    }

    public void setData(List<ProjectstateDataEntity> data) {
        this.data = data;
    }
}
