package com.example.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="每个用户的校园卡信息",description="用于校园卡认证")
public class SchoolCard {
    @ApiModelProperty(value = "用户openid",example = "****")
    private String openid;
    @ApiModelProperty(value = "用户校园卡url",example = "**.jpg")
    private String image_url;
    @ApiModelProperty(value = "用户认证状态",example = "1")
    private int state;
    @ApiModelProperty(value = "用户真实姓名",example = "王小明")
    private String realname;

    SchoolCard(){}
    public String getopenid() {
        return openid;
    }

    public void setopenid(String openid) {
        this.openid = openid;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}
