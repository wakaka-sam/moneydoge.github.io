package backend1.demo;

import java.util.Date;

public class second_hand {
    public int sid;
    public String object_name;
    public String content;
    public int pay;
    public Date ending_time;

    second_hand(int sid,String object_name,String content,int pay,Date ending_time){
        this.sid = sid;
        this.object_name = object_name;
        this.content = content;
        this.pay = pay;
        this.ending_time = ending_time;
    }

}
