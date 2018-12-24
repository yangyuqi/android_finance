package com.youzheng.zhejiang.financebooking.Model.user;

public class MInfoEntity {
    private String respCode ;
    private String respMsg ;
    private String member ;
    private MInfoAccountEntity account ;
    private MInfoMemberAuthEntity memberAuth ;

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

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public MInfoAccountEntity getAccount() {
        return account;
    }

    public void setAccount(MInfoAccountEntity account) {
        this.account = account;
    }

    public MInfoMemberAuthEntity getMemberAuth() {
        return memberAuth;
    }

    public void setMemberAuth(MInfoMemberAuthEntity memberAuth) {
        this.memberAuth = memberAuth;
    }
}
