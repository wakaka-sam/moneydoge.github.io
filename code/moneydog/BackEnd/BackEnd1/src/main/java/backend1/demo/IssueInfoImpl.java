package backend1.demo;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class IssueInfoImpl implements IssueInfoMapper {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int CreateUser(String openid, String nickName, String avatarUrl, String gender) {
        try{
            return jdbcTemplate.update("insert into moneydog.user(openid,nikname,avatarUrl,gender,state)values (?,?,?,?,0)", openid, nickName, avatarUrl, gender);
        }catch (Exception e){
            System.out.println("创建用户");
            System.out.println(e);
            return 0;
        }
    }

    @Override
    public int Login(String openid) {
        try {
            return jdbcTemplate.queryForObject("select 1 from user where openid = ? limit 1;", new Object[]{openid}, int.class);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public JSONObject CreateExpressage(String openid,String express_loc, Date arrive_time, String loc, int num, int pay, String remark, String phone, String wechat) {
        String id = openid;
        int t = 0;
        String msg = "Create Successfully!";
        JSONObject jsonObject = new JSONObject();

        try{
            t = jdbcTemplate.update("INSERT  into moneydog.expressage(uid1,express_loc,arrive_time,loc,num,pay,remark,phone,wechat,state)values (?,?,?,?,?,?,?,?,?,0)", id, express_loc, arrive_time, loc, num, pay, remark, phone, wechat);

        }catch (Exception e){
            System.out.println("创建快递");
            System.out.println(e);
        }
        if (t == 0) {
            msg = "Create Unsuccessfully!";
        }
        jsonObject.put("errcode", t);
        jsonObject.put("errmsg", msg);
        return jsonObject;
    }

    @Override
    public JSONObject CreateFor_help(String openid,String title, String content, String phone, String wechat, Date ending_time, int pay) {
        String id = openid;
        int t = 0;
        String msg = "Create Successfully!";
        JSONObject jsonObject = new JSONObject();
        try {
            t = jdbcTemplate.update("insert into moneydog.for_help(uid1,title,content,phone,wechat,ending_time,pay,state) values (?,?,?,?,?,?,?,0)", id, title, content, phone, wechat, ending_time, pay);

        }catch (Exception e){
            System.out.println("创建求助");
            System.out.println(e);
        }
        if (t == 0) {
            msg = "Create Unsuccessfully!";
        }
        jsonObject.put("errcode", t);
        jsonObject.put("errmsg", msg);
        return jsonObject;

    }

    @Override
    public JSONObject CreateErrand(String openid,String title, String content, String phone, String wechat, Date ending_time, int pay) {
        String id = openid;
        int t = 0;
        String msg = "Create Successfully!";
        JSONObject jsonObject = new JSONObject();
        try {
            t = jdbcTemplate.update("insert into moneydog.errand(uid1,title,content,phone,wechat,ending_time,pay,state) values (?,?,?,?,?,?,?,0)", id, title, content, phone, wechat, ending_time, pay);

        }catch (Exception e){
            System.out.println("创建跑腿");
            System.out.println(e);
        }
           if (t == 0) {
            msg = "Create Unsuccessfully!";
        }
        jsonObject.put("errcode", t);
        jsonObject.put("errmsg", msg);
        return jsonObject;
    }

    @Override
    public JSONObject CreateSecond_hand(String openid,String object_name, String content, String phone, String wechat, Date ending_time, int pay, String photo_url) {
        String id = openid;
        int t = 0;
        String msg = "Create Successfully!";
        JSONObject jsonObject = new JSONObject();
        try{
            t = jdbcTemplate.update("insert into moneydog.second_hand(uid1,object_name,content,phone,wechat,ending_time,pay,photo_url,state) values (?,?,?,?,?,?,?,?,0)", id, object_name, content, phone, wechat, ending_time, pay, photo_url);
        }catch (Exception e){
            System.out.println("创建闲置");
            System.out.println(e);

        }
               if (t == 0) {
            msg = "Create Unsuccessfully!";
        }
        jsonObject.put("errcode", t);
        jsonObject.put("errmsg", msg);
        return jsonObject;
    }
}
