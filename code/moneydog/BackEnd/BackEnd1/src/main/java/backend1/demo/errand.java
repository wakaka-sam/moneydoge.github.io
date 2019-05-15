package backend1.demo;

import java.util.Date;

public class errand {
    public int rid;
    public String title;
    public String content;
    public int pay;
    public Date ending_time;
    public int state;
    public Date issue_time;

    public int getState() {
        return state;
    }

    public Date getIssue_time() {
        return issue_time;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setIssue_time(Date issue_time) {
        this.issue_time = issue_time;
    }
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

    errand() {

    }
}
