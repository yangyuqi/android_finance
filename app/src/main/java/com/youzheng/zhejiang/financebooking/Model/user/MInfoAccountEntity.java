package com.youzheng.zhejiang.financebooking.Model.user;

public class MInfoAccountEntity {
    private Integer id ;
    private Integer mId ;
    private String virtualCard ;
    private Double accountAmount ;
    private Double availAmount ;
    private Integer accountStatus ;
    private String traderPassword ;
    private Double freezeAmount ;
    private String freezeDesc ;
    private Double redPacket ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getVirtualCard() {
        return virtualCard;
    }

    public void setVirtualCard(String virtualCard) {
        this.virtualCard = virtualCard;
    }

    public Double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(Double accountAmount) {
        this.accountAmount = accountAmount;
    }

    public Double getAvailAmount() {
        return availAmount;
    }

    public void setAvailAmount(Double availAmount) {
        this.availAmount = availAmount;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getTraderPassword() {
        return traderPassword;
    }

    public void setTraderPassword(String traderPassword) {
        this.traderPassword = traderPassword;
    }

    public Double getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(Double freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public String getFreezeDesc() {
        return freezeDesc;
    }

    public void setFreezeDesc(String freezeDesc) {
        this.freezeDesc = freezeDesc;
    }

    public Double getRedPacket() {
        return redPacket;
    }

    public void setRedPacket(Double redPacket) {
        this.redPacket = redPacket;
    }
}
