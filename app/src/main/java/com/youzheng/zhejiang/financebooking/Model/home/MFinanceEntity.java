package com.youzheng.zhejiang.financebooking.Model.home;

public class MFinanceEntity {
    private String respCode ;
    private String respMsg ;
    private MFinanceDataEntity data ;

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

    public MFinanceDataEntity getData() {
        return data;
    }

    public void setData(MFinanceDataEntity data) {
        this.data = data;
    }
}
