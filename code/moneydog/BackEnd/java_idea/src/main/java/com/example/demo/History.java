package com.example.demo;

import java.util.Date;

public class History {
    //private int thid;
    private String uid;
    private String type;
    private int price;
    private int finish;
    private Date time;
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
