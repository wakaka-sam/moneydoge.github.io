package backend1.demo;

import java.util.Date;

public interface IssueInfoMapper  {
//        从Header 里面获取id
    String CreateExpressage(String express_loc, Date arrive_time,String loc,int num,int pay,String remark,String phone,String wechat);
//    state 初始为0，到后面再添加
    String CreateFor_help(String title,String phone,String wechat,Date working_time,int pay);
    String CreateErrand(String title,String phone,String wechat,Date working_time,int pay);
    String CreateSecond_hand(String object_name,String phone,String wechat,Date working_time,int pay,String photo_url);
}
