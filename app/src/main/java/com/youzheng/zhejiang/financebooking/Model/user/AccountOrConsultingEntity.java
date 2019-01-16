package com.youzheng.zhejiang.financebooking.Model.user;

import java.util.List;

public class AccountOrConsultingEntity {


    /**
     * respCode : 1000
     * respMsg : success
     * data : [{"infoText":"industry_content_2","infoTitle":"industry_title_2","infoType":1,"releaseTime":"2019-01-01","releaseAdmin":"user","releaseStatus":2,"id":2,"infoView":1},{"infoText":"industry_content_1","infoTitle":"industry_title_1","infoType":1,"releaseTime":"2018-12-12","releaseAdmin":"admin","releaseStatus":2,"id":1,"infoView":1}]
     */

    private String respCode;
    private String respMsg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * infoText : industry_content_2
         * infoTitle : industry_title_2
         * infoType : 1
         * releaseTime : 2019-01-01
         * releaseAdmin : user
         * releaseStatus : 2
         * id : 2
         * infoView : 1
         */

        private String infoText;
        private String infoTitle;
        private int infoType;
        private String releaseTime;
        private String releaseAdmin;
        private int releaseStatus;
        private int id;
        private int infoView;

        public String getInfoText() {
            return infoText;
        }

        public void setInfoText(String infoText) {
            this.infoText = infoText;
        }

        public String getInfoTitle() {
            return infoTitle;
        }

        public void setInfoTitle(String infoTitle) {
            this.infoTitle = infoTitle;
        }

        public int getInfoType() {
            return infoType;
        }

        public void setInfoType(int infoType) {
            this.infoType = infoType;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getReleaseAdmin() {
            return releaseAdmin;
        }

        public void setReleaseAdmin(String releaseAdmin) {
            this.releaseAdmin = releaseAdmin;
        }

        public int getReleaseStatus() {
            return releaseStatus;
        }

        public void setReleaseStatus(int releaseStatus) {
            this.releaseStatus = releaseStatus;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getInfoView() {
            return infoView;
        }

        public void setInfoView(int infoView) {
            this.infoView = infoView;
        }
    }
}
