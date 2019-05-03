package backend1.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class IssueInfoImpl implements IssueInfoMapper{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int  CreateUser(String openid,String nickName,String avatarUrl,String gender){
        return jdbcTemplate.update("insert into moneydog.user(openid,nikname,avatarUrl,gender,state)values (?,?,?,?,0)",openid,nickName,avatarUrl,gender);

    }
    @Override
    public int CreateExpressage(String express_loc, Date arrive_time,String loc,int num,int pay,String remark,String phone,String wechat){
        String id = "001";
        return jdbcTemplate.update("INSERT  into moneydog.expressage(uid1,express_loc,arrive_time,loc,num,pay,remark,phone,wechat)values (?,?,?,?,?,?,?,?,?)" ,id,express_loc,arrive_time,loc,num,pay,remark,phone,wechat);
    }
    @Override
    public int CreateFor_help(String title,String content,String phone,String wechat,Date ending_time,int pay){
        String id = "001";
        return jdbcTemplate.update("insert into moneydog.for_help(uid1,title,content,phone,wechat,ending_time,pay) values (?,?,?,?,?,?,?)",id,title,content,phone,wechat,ending_time,pay);



    }
    @Override
    public int CreateErrand(String title,String content ,String phone,String wechat,Date ending_time,int pay){
        String id = "001";
        return jdbcTemplate.update("insert into moneydog.errand(uid1,title,content,phone,wechat,ending_time,pay) values (?,?,?,?,?,?,?)",id,title,content,phone,wechat,ending_time,pay);

    }
    @Override
    public int CreateSecond_hand(String object_name,String content,String phone,String wechat,Date ending_time,int pay,String photo_url){
        String id = "001";
        return jdbcTemplate.update("insert into moneydog.second_hand(uid1,object_name,content,phone,wechat,ending_time,pay,photo_url) values (?,?,?,?,?,?,?,?)",id,object_name,content,phone,wechat,ending_time,pay,photo_url);
    }
}
