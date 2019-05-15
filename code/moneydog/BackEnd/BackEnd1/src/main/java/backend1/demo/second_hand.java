package backend1.demo;

import java.util.Date;

public class second_hand {
    private int sid;
    private String object_name;
    private String content;
    private Date ending_time;
    private String photo_url;
    private int pay;
    private int state;

    private Date issue_time;

    public int getSid() {
        return sid;
    }

    public String getObject_name() {
        return object_name;
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

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
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

    public second_hand() {

    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getIssue_time() {
        return issue_time;
    }

    public void setIssue_time(Date issue_time) {
        this.issue_time = issue_time;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
}
