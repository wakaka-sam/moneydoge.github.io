package backend1.demo;

import java.util.Date;

public class expressage_detail {
    public String express_loc;
    public Date arrive_time;
    public int num;
    public int pay;
    public String loc;
    public int pid;
    public String uid1;
    public String uid2;
    public String phone;
    public String wechat;
    public String remark;
    public Date issue_time;
    public int state;

    public void setState(int state) {
        this.state = state;
    }

    public void setIssue_time(Date issue_time) {
        this.issue_time = issue_time;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public void setPay(int pay) {
        this.pay = pay;
    }

    public void setArrive_time(Date arrive_time) {
        this.arrive_time = arrive_time;
    }

    public void setExpress_loc(String express_loc) {
        this.express_loc = express_loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPay() {
        return pay;
    }

    public Date getArrive_time() {
        return arrive_time;
    }

    public int getNum() {
        return num;
    }

    public int getPid() {
        return pid;
    }

    public String getExpress_loc() {
        return express_loc;
    }

    public String getLoc() {
        return loc;
    }

    public Date getIssue_time() {
        return issue_time;
    }

    public String getPhone() {
        return phone;
    }

    public String getRemark() {
        return remark;
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

    public int getState() {
        return state;
    }
}
