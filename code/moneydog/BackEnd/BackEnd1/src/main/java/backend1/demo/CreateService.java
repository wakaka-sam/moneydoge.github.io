package backend1.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CreateService {
    @Autowired
    IssueInfoMapper issueInfoMapper;
    //        从Header 里面获取id
    public String CreateExpressage(String express_loc, Date arrive_time, String loc, int num, int pay, String remark, String phone, String wechat){
        return  issueInfoMapper.CreateExpressage(express_loc,arrive_time,loc,num,pay,remark,phone,wechat);
    }
    //    state 初始为0，到后面再添加
    public String CreateFor_help(String title,String phone,String wechat,Date working_time,int pay){
        return   issueInfoMapper.CreateFor_help(title,phone,wechat,working_time,pay);
    }
    public String CreateErrand(String title,String phone,String wechat,Date working_time,int pay){
        return   issueInfoMapper.CreateErrand(title,phone,wechat,working_time,pay);
    }
    public String CreateSecond_hand(String object_name,String phone,String wechat,Date working_time,int pay,String photo_url){
        return  issueInfoMapper.CreateSecond_hand(object_name,phone,wechat,working_time,pay,photo_url);
    }


}
