package com.example.demo;

import io.swagger.annotations.ApiModelProperty;

public class User {
    @ApiModelProperty(value = "学校")
    private String school;
    @ApiModelProperty(value = "手机号")
    private String phoneNum;
    @ApiModelProperty(value = "真实名字")
    private String realname;
    @ApiModelProperty(value = "昵称")
    private String falsename;


    User(){

    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getFalsename() {
        return falsename;
    }

    public void setFalsename(String falsename) {
        this.falsename = falsename;
    }
}
