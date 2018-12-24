package com.youzheng.zhejiang.financebooking.Model.user;

public class MInvestmentActivityEntity {
    private String respCode ;
    private String respMsg ;
    private MInvestmentActivityDataEntity  data ;

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

    public MInvestmentActivityDataEntity getData() {
        return data;
    }

    public void setData(MInvestmentActivityDataEntity data) {
        this.data = data;
    }
}
