package com.youzheng.zhejiang.financebooking.Model.home;

public class MVersionEntity {
    private String respCode ;
    private String respMsg ;
    private MVersionDataMEntity data ;

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

    public MVersionDataMEntity getData() {
        return data;
    }

    public void setData(MVersionDataMEntity data) {
        this.data = data;
    }
}
