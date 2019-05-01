package backend1.demo;

import java.util.Date;

public class expressage {
    public String express_loc;
    public Date arrive_time;
    public int num;
    public int pay;
    public String loc;
    public int pid;

    expressage(String express_loc,Date arrive_time,int num,int pay,String loc,int pid){
        this.express_loc = express_loc;
        this.arrive_time = arrive_time;
        this.num = num;
        this.pay = pay;
        this.loc = loc;
        this.pid = pid;
    }

}
