package backend1.demo;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LoadImpl implements LoadMapper {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<expressage> downLoadExpressage(int pid) {
        //按从大到小排列，第一个即为最新的消息，不过前端需要记录list尾部的id（队列中最早的消息），再次下拉时需要将该id传给后端
        List<expressage> temp = jdbcTemplate.query("select pid,express_loc,arrive_time,loc,num,pay,state,remark,issue_time from moneydog.expressage where pid <= ? and state = 0 order by pid DESC limit 15 ; ", new Object[]{pid}, new BeanPropertyRowMapper(expressage.class));
        return temp;
    }

    @Override
    public List<errand> downLoadErrands(int rid) {
        List<errand> temp = jdbcTemplate.query("select rid,title,content,ending_time,pay,state,issue_time from moneydog.errand where rid <= ? and state = 0 order by rid desc limit 15 ; ", new Object[]{rid}, new BeanPropertyRowMapper(errand.class));
        return temp;
    }

    @Override
    public List<for_help> downLoadFor_help(int fid) {
        List<for_help> temp = jdbcTemplate.query("select fid,title,content,ending_time,pay,state,issue_time from moneydog.for_help where fid <= ? and state = 0 order by fid desc limit 15 ; ", new Object[]{fid}, new BeanPropertyRowMapper(for_help.class));
        return temp;
    }

    @Override
    public List<second_hand> downLoadSecond_hand(int sid) {
        List<second_hand> temp = jdbcTemplate.query("select sid,object_name,content,pay,ending_time,photo_url,state,issue_time from moneydog.second_hand where sid <= ? and state = 0 order by sid desc limit 15 ;", new Object[]{sid}, new BeanPropertyRowMapper(second_hand.class));
        System.out.println(temp.toString());
        return temp;
    }

    @Override
    public List<expressage> OnLoadExpressage() {
        // 获取最大的id，即最新的消息id，找出15条返回
        int id = jdbcTemplate.queryForObject("SELECT COUNT(pid) AS NumberOfProducts FROM expressage;", int.class);
        List<expressage> temp = jdbcTemplate.query("select pid,express_loc,arrive_time,loc,num,pay,state,remark,issue_time from moneydog.expressage where pid <= ? and state = 0 order by pid DESC limit 15 ; ", new Object[]{id}, new BeanPropertyRowMapper(expressage.class));
        return temp;
    }

    @Override
    public List<errand> OnLoadErrands() {
        int id = jdbcTemplate.queryForObject("SELECT COUNT(rid) AS NumberOfProducts FROM errand;", int.class);
        List<errand> temp = jdbcTemplate.query("select rid,title,content,ending_time,pay,state,issue_time from moneydog.errand where rid <= ? and state = 0 order by rid desc limit 15 ; ", new Object[]{id}, new BeanPropertyRowMapper(errand.class));
        return temp;
    }

    @Override
    public List<for_help> OnLoadFor_help() {
        int id = jdbcTemplate.queryForObject("SELECT COUNT(fid) AS NumberOfProducts FROM for_help;", int.class);
        List<for_help> temp = jdbcTemplate.query("select fid,title,content,ending_time,pay,state,issue_time from moneydog.for_help where fid <= ? and state = 0 order by fid desc limit 15 ;", new Object[]{id}, new BeanPropertyRowMapper(for_help.class));
        if (temp != null && temp.size() > 0) {
            return temp;
        }
        return null;
    }

    @Override
    public List<second_hand> OnLoadSecond_hand() {

        int id = jdbcTemplate.queryForObject("SELECT COUNT(sid) AS NumberOfProducts FROM second_hand;", int.class);
        List<second_hand> temp = jdbcTemplate.query("select sid,object_name,content,ending_time,photo_url,pay,state,issue_time from moneydog.second_hand where sid <= ? and state = 0 order by sid desc limit 15 ;", new Object[]{id}, new BeanPropertyRowMapper(second_hand.class));

        if (temp != null && temp.size() > 0) {
            System.out.println(temp.toString());
            return temp;
        }
        return null;
    }

    @Override
    public JSONObject LoadMyCreation(String openid) {
        JSONObject jsonObject = new JSONObject();

        String expressageSql = "select pid,express_loc,arrive_time,loc,num,pay,state,remark,issue_time from moneydog.expressage where uid1 = ? and state != 6;";
        String errandSql = "select rid,title,content,ending_time,pay,state,issue_time from moneydog.errand where uid1 = ? and state != 6;";
        String second_handSql = "select sid,object_name,content,ending_time,photo_url,pay,state,issue_time from moneydog.second_hand where uid1 = ? and state != 6;";
        String for_helpSql = "select fid,title,content,ending_time,pay,state,issue_time from moneydog.for_help where uid1 = ? and state != 6;";

        jsonObject.put("expressages", "");
        jsonObject.put("errands", "");
        jsonObject.put("for_helps", "");
        jsonObject.put("second_hands", "");
        try {
            List<expressage> expressages = jdbcTemplate.query(expressageSql, new Object[]{openid}, new BeanPropertyRowMapper(expressage.class));
            jsonObject.put("expressages", expressages);
        }catch (Exception e){

        }
        try {
            List<errand> errands = jdbcTemplate.query(errandSql, new Object[]{openid}, new BeanPropertyRowMapper(errand.class));
            jsonObject.put("errands", errands);
        }catch (Exception e){

        }
        try {
            List<for_help> for_helps = jdbcTemplate.query(for_helpSql, new Object[]{openid}, new BeanPropertyRowMapper(for_help.class));
            jsonObject.put("for_helps", for_helps);
        }catch (Exception e){

        }
        try {
            List<second_hand> second_hands = jdbcTemplate.query(second_handSql, new Object[]{openid}, new BeanPropertyRowMapper(second_hand.class));
            jsonObject.put("second_hands", second_hands);
        }catch (Exception e){

        }
        return jsonObject;
    }

    @Override
    public JSONObject LoadMyReceiving(String openid) {
        JSONObject jsonObject = new JSONObject();

        String expressageSql = "select  pid,express_loc,arrive_time,loc,num,pay,state,remark,issue_time from moneydog.expressage where uid2 = ? and state != 6;";
        String errandSql = "select rid,title,content,ending_time,pay,state,issue_time from moneydog.errand where uid2 = ? and state != 6;";
        String second_handSql = "select sid,object_name,content,ending_time,photo_url,pay,state,issue_time from moneydog.second_hand where uid2 = ? and state != 6;";
        String for_helpSql = "select fid,title,content,ending_time,pay,state,issue_time from moneydog.for_help where uid2 = ? and state != 6;";


        jsonObject.put("expressages", "");
        jsonObject.put("errands", "");
        jsonObject.put("for_helps", "");
        jsonObject.put("second_hands", "");

        try {
            List<expressage> expressages = jdbcTemplate.query(expressageSql, new Object[]{openid}, new BeanPropertyRowMapper(expressage.class));
            jsonObject.put("expressages", expressages);
        }catch (Exception e){
            System.out.println(e);
        }
        try {
            List<errand> errands  = jdbcTemplate.query(errandSql, new Object[]{openid}, new BeanPropertyRowMapper(errand.class));
            jsonObject.put("errands", errands);
        }catch (Exception e){
            System.out.println(e);
        }
        try {

            List<for_help> for_helps = jdbcTemplate.query(for_helpSql, new Object[]{openid}, new BeanPropertyRowMapper(for_help.class));
            jsonObject.put("for_helps", for_helps);
        }catch (Exception e){
            System.out.println(e);
        }
        try {
            List<second_hand> second_hands = jdbcTemplate.query(second_handSql, new Object[]{openid}, new BeanPropertyRowMapper(second_hand.class));
            jsonObject.put("second_hands", second_hands);
        }catch (Exception e){
            System.out.println(e);
        }

        return jsonObject;
    }
}
