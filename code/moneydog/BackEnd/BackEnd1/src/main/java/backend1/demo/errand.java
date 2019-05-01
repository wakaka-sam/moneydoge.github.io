package backend1.demo;

import java.util.Date;

public class errand {
    public int rid;
    public String  title;
    public String  content;
    public int pay;
    public Date ending_time;

    errand(int rid,String  title,String content,int pay,Date ending_time){
        this.rid = rid;
        this.title = title;
        this.content = content;
        this.pay = pay;
        this.ending_time = ending_time;
    }
}
