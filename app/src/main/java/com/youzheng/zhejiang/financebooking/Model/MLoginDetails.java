package com.youzheng.zhejiang.financebooking.Model;

import com.youzheng.zhejiang.financebooking.Model.home.MLoginGradeEntity;

public class MLoginDetails {
    private String respCode ;
    private String respMsg ;
    private Integer isAuth ;
    private MLoginGradeEntity grade ;
    private String invitaionCode ;
    private String emailStatus ;
    private String userName ;
    private String token ;

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

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    public MLoginGradeEntity getGrade() {
        return grade;
    }

    public void setGrade(MLoginGradeEntity grade) {
        this.grade = grade;
    }

    public String getInvitaionCode() {
        return invitaionCode;
    }

    public void setInvitaionCode(String invitaionCode) {
        this.invitaionCode = invitaionCode;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
