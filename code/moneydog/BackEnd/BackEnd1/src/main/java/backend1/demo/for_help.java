package backend1.demo;

import java.util.Date;

public class for_help {
    public int fid;
    public String title;
    public String content;
    public Date  ending_time;
    public int pay;

    for_help(int fid,String title,String content,Date ending_time,int pay){
        this.fid = fid;
        this.title = title;
        this.content = content;
        this.ending_time = ending_time;
        this.pay = pay;
    }

}
