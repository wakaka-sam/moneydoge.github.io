package backend1.demo;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LoadImpl implements LoadMapper {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<expressage> downLoadExpressage(int pid){
        //按从大到小排列，第一个即为最新的消息，不过前端需要记录list尾部的id（队列中最早的消息），再次下拉时需要将该id传给后端
        List<expressage> temp =  jdbcTemplate.query("select express_loc,arrive_time,num,pay,loc,pid from moneydog.expressage where pid <= ? order by pid DESC limit 15 ; ",new Object[]{pid},new BeanPropertyRowMapper(expressage.class));
        return  temp;
    }
    @Override
    public List<errand> downLoadErrands(int rid){
        List<errand> temp =  jdbcTemplate.query("select rid,title,content,pay,ending_time from moneydog.errand where rid <= ? order by rid desc limit 15 ; ",new Object[]{rid},new BeanPropertyRowMapper(errand.class));
        return  temp;
    }
    @Override
    public List<for_help> downLoadFor_help(int fid){
        List<for_help> temp =  jdbcTemplate.query("select fid,title,content,ending_time,pay from moneydog.for_help where fid <= ? order by fid desc limit 15 ; ",new Object[]{fid},new BeanPropertyRowMapper(for_help.class));
        return  temp;
    }
    @Override
    public List<second_hand> downLoadSecond_hand(int sid){
        List<second_hand> temp =  jdbcTemplate.query("select sid,object_name,content,pay,ending_time from moneydog.second_hand where sid <= ? order by sid desc limit 15 ;",new Object[]{sid},new BeanPropertyRowMapper(second_hand.class));
        System.out.println(temp.toString());
        return  temp;
    }
    @Override
    public List<expressage> OnLoadExpressage(){
        // 获取最大的id，即最新的消息id，找出15条返回
        int id = jdbcTemplate.queryForObject("SELECT COUNT(pid) AS NumberOfProducts FROM expressage;",int.class);
        List<expressage> temp =  jdbcTemplate.query("select express_loc,arrive_time,num,pay,loc,pid from moneydog.expressage where pid <= ? order by pid DESC limit 15 ; ",new Object[]{id},new BeanPropertyRowMapper(expressage.class));
        return  temp;
    }
    @Override
    public List<errand> OnLoadErrands(){
        int id = jdbcTemplate.queryForObject("SELECT COUNT(rid) AS NumberOfProducts FROM errand;",int.class);
        List<errand> temp =  jdbcTemplate.query("select rid,title,content,pay,ending_time from moneydog.errand where rid <= ? order by rid desc limit 15 ; ",new Object[]{id},new BeanPropertyRowMapper(errand.class));

        return  temp;
    }
    @Override
    public List<for_help> OnLoadFor_help(){
        int id = jdbcTemplate.queryForObject("SELECT COUNT(fid) AS NumberOfProducts FROM for_help;",int.class);
        List<for_help> temp =  jdbcTemplate.query("select fid,title,content,ending_time,pay from moneydog.for_help where fid <= ? order by fid desc limit 15 ;",new Object[]{id},new BeanPropertyRowMapper(for_help.class));
        if(temp != null && temp.size()> 0){
            return  temp;
        }
        return  null;
    }
    @Override
    public List<second_hand> OnLoadSecond_hand(){


        int id = jdbcTemplate.queryForObject("SELECT COUNT(sid) AS NumberOfProducts FROM second_hand;",int.class);
        List<second_hand> temp =  jdbcTemplate.query("select sid,object_name,content,pay,ending_time from moneydog.second_hand where sid <= ? order by sid desc limit 15 ;",new Object[]{id},new BeanPropertyRowMapper(second_hand.class));

        if(temp != null && temp.size()> 0){
            System.out.println(temp.toString());
            return  temp;
        }
        return  null;
    }
    @Override
    public JSONObject LoadMyCreation(String openid){
        JSONObject jsonObject = new JSONObject();

        String expressageSql = "select pid,loc,state from moneydog.expression where uid1 == ?";
        String errandSql = "select rid,title,content,state from moneydog.errand where uid1 == ?";
        String second_handSql = "select sid,object_name,content,state from moneydog.second_hand where uid1 == ?";
        String for_helpSql = "select fid,title,content,state from moneydog.for_help where uid1 == ?";

        List<expressage> expressages = jdbcTemplate.query(expressageSql ,new Object[]{openid},new BeanPropertyRowMapper(expressage.class));
        List<errand> errands = jdbcTemplate.query(errandSql,new Object[]{openid},new BeanPropertyRowMapper(errand.class));
        List<for_help> for_helps = jdbcTemplate.query(for_helpSql,new Object[]{openid},new BeanPropertyRowMapper(for_help.class));
        List<second_hand> second_hands = jdbcTemplate.query(second_handSql,new Object[]{openid},new BeanPropertyRowMapper(second_hand.class));

        jsonObject.put("expressages",expressages);
        jsonObject.put("errands",errands);
        jsonObject.put("for_helps",for_helps);
        jsonObject.put("second_hands",second_hands);

        return  jsonObject;
    }

    @Override
    public JSONObject LoadMyReceiving(String openid) {
        JSONObject jsonObject = new JSONObject();

        String expressageSql = "select pid,loc,state from moneydog.expression where uid2 == ?";
        String errandSql = "select rid,title,content,state from moneydog.errand where uid3 == ?";
        String second_handSql = "select sid,object_name,content,state from moneydog.second_hand where uid3 == ?";
        String for_helpSql = "select fid,title,content,state from moneydog.for_help where uid3 == ?";

        List<expressage> expressages = jdbcTemplate.query(expressageSql ,new Object[]{openid},new BeanPropertyRowMapper(expressage.class));
        List<errand> errands = jdbcTemplate.query(errandSql,new Object[]{openid},new BeanPropertyRowMapper(errand.class));
        List<for_help> for_helps = jdbcTemplate.query(for_helpSql,new Object[]{openid},new BeanPropertyRowMapper(for_help.class));
        List<second_hand> second_hands = jdbcTemplate.query(second_handSql,new Object[]{openid},new BeanPropertyRowMapper(second_hand.class));

        jsonObject.put("expressages",expressages);
        jsonObject.put("errands",errands);
        jsonObject.put("for_helps",for_helps);
        jsonObject.put("second_hands",second_hands);
        return  jsonObject;
    }
}
