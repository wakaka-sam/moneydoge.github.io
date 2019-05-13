package backend1.demo;

import java.util.Date;

public class for_help_detail {
    public int fid;
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

    public int getState() {
        return state;
    }

    public String getWechat() {
        return wechat;
    }

    public String getUid2() {
        return uid2;
    }

    public String getUid1() {
        return uid1;
    }

    public String getPhone() {
        return phone;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setIssue_time(Date issue_time) {
        this.issue_time = issue_time;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public void setUid2(String uid2) {
        this.uid2 = uid2;
    }

    public void setUid1(String uid1) {
        this.uid1 = uid1;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public Date getEnding_time() {
        return ending_time;
    }

    public int getFid() {
        return fid;
    }

    public int getPay() {
        return pay;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEnding_time(Date ending_time) {
        this.ending_time = ending_time;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }



    for_help_detail() {

    }
}
