package com.youzheng.zhejiang.financebooking.Model.home;

public class MAvailMoneyEntity {
    private String respCode ;
    private String respMsg ;
    private String phone ;
    private Double availMoney ;
    private Double productAvailAmount ;
    private Double minInvest ;
    private Double maxInvest ;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getAvailMoney() {
        return availMoney;
    }

    public void setAvailMoney(Double availMoney) {
        this.availMoney = availMoney;
    }

    public Double getProductAvailAmount() {
        return productAvailAmount;
    }

    public void setProductAvailAmount(Double productAvailAmount) {
        this.productAvailAmount = productAvailAmount;
    }

    public Double getMinInvest() {
        return minInvest;
    }

    public void setMinInvest(Double minInvest) {
        this.minInvest = minInvest;
    }

    public Double getMaxInvest() {
        return maxInvest;
    }

    public void setMaxInvest(Double maxInvest) {
        this.maxInvest = maxInvest;
    }
}
