package com.example.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="每个具体用户",description="用于用户信息更改管理")
public class User {
    @ApiModelProperty(value = "学校",example = "中山大学")
    private String school;
    @ApiModelProperty(value = "手机号",example = "110")
    private String phoneNum;
    @ApiModelProperty(value = "真实名字",example = "王小明")
    private String realname;
    @ApiModelProperty(value = "昵称",example = "小明同学")
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
