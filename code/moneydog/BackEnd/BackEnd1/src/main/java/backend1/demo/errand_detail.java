package backend1.demo;

import java.util.Date;

public class errand_detail {
    public int rid;
    private String uid1;
    private String uid2;
    public String title;
    public String content;
    private String phone;
    private String wechat;
    public Date ending_time;
    public int pay;
    private int state;
    private Date issue_time;


    public int getRid() {
        return rid;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getPay() {
        return pay;
    }

    public Date getEnding_time() {
        return ending_time;
    }

    public String getPhone() {
        return phone;
    }

    public String getUid1() {
        return uid1;
    }

    public String getUid2() {
        return uid2;
    }

    public String getWechat() {
        return wechat;
    }

    public Date getIssue_time() {
        return issue_time;
    }

    public int getState() {
        return state;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUid1(String uid1) {
        this.uid1 = uid1;
    }

    public void setUid2(String uid2) {
        this.uid2 = uid2;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public void setIssue_time(Date issue_time) {
        this.issue_time = issue_time;
    }

    public void setState(int state) {
        this.state = state;
    }
    public void setRid(int rid) {
        this.rid = rid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public void setEnding_time(Date ending_time) {
        this.ending_time = ending_time;
    }
    errand_detail(){

    }
}
