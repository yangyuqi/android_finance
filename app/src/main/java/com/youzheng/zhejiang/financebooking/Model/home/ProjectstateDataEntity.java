package com.youzheng.zhejiang.financebooking.Model.home;

public class ProjectstateDataEntity {
    private Integer id ;
    private Integer orderId ;
    private String bidder ;
    private Double orderAccount ;
    private String effectiveDate ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getBidder() {
        return bidder;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }

    public Double getOrderAccount() {
        return orderAccount;
    }

    public void setOrderAccount(Double orderAccount) {
        this.orderAccount = orderAccount;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
