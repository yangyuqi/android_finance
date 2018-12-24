package com.youzheng.zhejiang.financebooking.Model.user;

import java.util.List;

public class MFinanceStatusEntity {
    private String respCode ;
    private String respMsg ;
    private Integer pageSize ;
    private Integer pageIndex ;
    private Integer startIndex ;
    private List<MFinanceStatusDataEntity> data ;
    private Integer count ;

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

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public List<MFinanceStatusDataEntity> getData() {
        return data;
    }

    public void setData(List<MFinanceStatusDataEntity> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
