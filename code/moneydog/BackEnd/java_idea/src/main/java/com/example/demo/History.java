package com.example.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value="余额明细记录",description="用于余额明细管理")
public class History {
    //private int thid;
    @ApiModelProperty(value = "手机号",example = "110")
    private String uid;
    @ApiModelProperty(value = "手机号",example = "110")
    private String type;
    @ApiModelProperty(value = "手机号",example = "110")
    private int price;
    @ApiModelProperty(value = "手机号",example = "110")
    private int finish;
    @ApiModelProperty(value = "手机号",example = "110")
    private Date time;
    @ApiModelProperty(value = "手机号",example = "110")
    private String detail;

    History()
    {

    }
    /*
    public int getThid() {
        return thid;
    }

    public void setThid(int thid) {
        this.thid = thid;
    }
*/
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }
}
