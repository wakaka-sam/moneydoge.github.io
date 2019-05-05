package backend1.demo;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CreateService {
    @Autowired
    IssueInfoMapper issueInfoMapper;

    public int CreateUser(String openid, String nickName, String avatarUrl, String gender){
        return   issueInfoMapper.CreateUser(openid,nickName,avatarUrl,gender);
    }
    //        从Header 里面获取id
    public JSONObject CreateExpressage(String express_loc, Date arrive_time, String loc, int num, int pay, String remark, String phone, String wechat){
        return  issueInfoMapper.CreateExpressage(express_loc,arrive_time,loc,num,pay,remark,phone,wechat);
    }
    //    state 初始为0，到后面再添加
    public JSONObject CreateFor_help(String title,String content,String phone,String wechat,Date ending_time,int pay){
        return   issueInfoMapper.CreateFor_help(title,content,phone,wechat,ending_time,pay);
    }
    public JSONObject CreateErrand(String title,String content,String phone,String wechat,Date ending_time,int pay){
        return   issueInfoMapper.CreateErrand(title,content,phone,wechat,ending_time,pay);
    }
    public JSONObject CreateSecond_hand(String object_name,String content,String phone,String wechat,Date ending_time,int pay,String photo_url){
        return  issueInfoMapper.CreateSecond_hand(object_name,content,phone,wechat,ending_time,pay,photo_url);
    }
    public int Login(String openid){
        return  issueInfoMapper.Login(openid);
    }


}
