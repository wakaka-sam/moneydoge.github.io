package Load;

import java.util.Date;

public class second_hand {
    private int sid;
    private String object_name;
    private String content;
    private int pay;
    private Date ending_time;

    public int getSid(){
        return sid;
    }
    public String getObject_name(){return object_name;}
    public String getContent(){return content;}
    public int getPay(){return  pay;}
    public Date getEnding_time(){
        return ending_time;
    }
    public void setSid(int sid){
        this.sid = sid;
    }
    public void setObject_name(String object_name){
        this.object_name = object_name;
    }
    public void setContent(String content){
        this.content = content;
    }
    public void setPay(int pay){
        this.pay = pay;
    }
    public void setEnding_time(Date ending_time){
        this.ending_time = ending_time;
    }
    public second_hand(){

    }

}
