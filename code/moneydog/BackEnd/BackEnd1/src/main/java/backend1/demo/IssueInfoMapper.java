package backend1.demo;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public interface IssueInfoMapper  {
    int CreateUser(String openid, String nikName, String avatarUrl, String gender);
    //        从Header 里面获取id
    JSONObject CreateExpressage(String express_loc, Date arrive_time,String loc,int num,int pay,String remark,String phone,String wechat);
    //    state 初始为0，到后面再添加
    JSONObject CreateFor_help(String title,String content, String phone,String wechat,Date ending_time,int pay);
    JSONObject CreateErrand(String title,String content, String phone,String wechat,Date ending_time,int pay);
    JSONObject CreateSecond_hand(String object_name,String content, String phone,String wechat,Date ending_time,int pay,String photo_url);
    int Login(String openid);
}
