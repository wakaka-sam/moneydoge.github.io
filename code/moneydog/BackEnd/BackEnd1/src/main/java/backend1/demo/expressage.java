package backend1.demo;

import java.util.Date;

public class expressage {
    public String express_loc;
    public Date arrive_time;
    public int num;
    public int pay;
    public String loc;
    public int pid;

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
    expressage(){

    }
}
