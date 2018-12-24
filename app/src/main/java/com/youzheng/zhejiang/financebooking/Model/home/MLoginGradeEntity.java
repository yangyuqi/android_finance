package com.youzheng.zhejiang.financebooking.Model.home;

public class MLoginGradeEntity {
    private Integer id ;
    private String gradeName ;
    private String gradeImg ;
    private Integer gradeQuotaBeg ;
    private Integer gradeQuotaEnd ;
    private String gradeDesc ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeImg() {
        return gradeImg;
    }

    public void setGradeImg(String gradeImg) {
        this.gradeImg = gradeImg;
    }

    public Integer getGradeQuotaBeg() {
        return gradeQuotaBeg;
    }

    public void setGradeQuotaBeg(Integer gradeQuotaBeg) {
        this.gradeQuotaBeg = gradeQuotaBeg;
    }

    public Integer getGradeQuotaEnd() {
        return gradeQuotaEnd;
    }

    public void setGradeQuotaEnd(Integer gradeQuotaEnd) {
        this.gradeQuotaEnd = gradeQuotaEnd;
    }

    public String getGradeDesc() {
        return gradeDesc;
    }

    public void setGradeDesc(String gradeDesc) {
        this.gradeDesc = gradeDesc;
    }
}
