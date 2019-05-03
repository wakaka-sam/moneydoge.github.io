package Load;

import java.util.Date;

public class for_help {
    public int fid;
    public String title;
    public String content;
    public Date  ending_time;
    public int pay;

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
    for_help(){

    }
}
