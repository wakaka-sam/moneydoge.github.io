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
    public String CreateExpressage(String express_loc, Date arrive_time,String loc,int num,int pay,String remark,String phone,String wechat){
        String id = "001";
        jdbcTemplate.update("INSERT  into moneydoge.expressage(uid1,express_loc,arrive_time,loc,num,pay,remark,phone,wechat)values (?,?,?,?,?,?,?,?,?)" ,id,express_loc,arrive_time,loc,num,pay,remark,phone,wechat);

        return  "1";
    }
    @Override
    public String CreateFor_help(String title,String phone,String wechat,Date working_time,int pay){
        String id = "001";
        int t =   jdbcTemplate.update("insert into moneydoge.for_help(uid1,title,phone,wechat,working_time,pay) values (?,?,?,?,?,?)",id,title,phone,wechat,working_time,pay);
        System.out.println(t);

        return  "1";
    }
    @Override
    public String CreateErrand(String title,String phone,String wechat,Date working_time,int pay){
        String id = "001";
        jdbcTemplate.update("insert into moneydoge.errand(uid1,title,phone,wechat,working_time,pay) values (?,?,?,?,?,?)",id,title,phone,wechat,working_time,pay);
        return  "1";
    }
    @Override
    public String CreateSecond_hand(String object_name,String phone,String wechat,Date working_time,int pay,String photo_url){
        String id = "001";
        jdbcTemplate.update("insert into moneydoge.errand(uid1,object_name,phone,wechat,working_time,pay,photo_url) values (?,?,?,?,?,?,?)",id,object_name,phone,wechat,working_time,pay,photo_url);
        return  "1";
    }
}
