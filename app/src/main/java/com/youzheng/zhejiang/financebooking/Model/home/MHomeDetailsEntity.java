package com.youzheng.zhejiang.financebooking.Model.home;

public class MHomeDetailsEntity {
    private Integer id ;
    private String productName ;
    private Integer productAccount ;
    private Integer productCycle ;
    private Double productAccural ;
    private double buyStatus ;
    private Integer residueAccount ;
    private String productEnddate ;
    private Integer productStatus ;

    private Double subscribeMax ;
    private Double subscribeMin ;

    public Double getSubscribeMax() {
        return subscribeMax;
    }

    public void setSubscribeMax(Double subscribeMax) {
        this.subscribeMax = subscribeMax;
    }

    public Double getSubscribeMin() {
        return subscribeMin;
    }

    public void setSubscribeMin(Double subscribeMin) {
        this.subscribeMin = subscribeMin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductAccount() {
        return productAccount;
    }

    public void setProductAccount(Integer productAccount) {
        this.productAccount = productAccount;
    }

    public Integer getProductCycle() {
        return productCycle;
    }

    public void setProductCycle(Integer productCycle) {
        this.productCycle = productCycle;
    }

    public Double getProductAccural() {
        return productAccural;
    }

    public void setProductAccural(Double productAccural) {
        this.productAccural = productAccural;
    }

    public double getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(double buyStatus) {
        this.buyStatus = buyStatus;
    }

    public Integer getResidueAccount() {
        return residueAccount;
    }

    public void setResidueAccount(Integer residueAccount) {
        this.residueAccount = residueAccount;
    }

    public String getProductEnddate() {
        return productEnddate;
    }

    public void setProductEnddate(String productEnddate) {
        this.productEnddate = productEnddate;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }
}
