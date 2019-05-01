package backend1.demo;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LoadImpl implements LoadMapper {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<expressage> downLoadExpressage(int pid){
        //按从大到小排列，第一个即为最新的消息，不过前端需要记录list尾部的id（队列中最早的消息），再次下拉时需要将该id传给后端
        List<expressage> temp =  jdbcTemplate.query("select express_loc,arrive_time,num,pay,loc,pid from moneydog.expressage where pid <= ? limited 15 order by pid DESC; ",new Object[]{pid},new BeanPropertyRowMapper(expressage.class));
        return  temp;
    }
    @Override
    public List<errand> downLoadErrands(int rid){
        List<errand> temp =  jdbcTemplate.query("select rid,title,content,pay,ending_time from moneydog.errand where rid <= ? limited 15 order by rid desc; ",new Object[]{rid},new BeanPropertyRowMapper(errand.class));
        return  temp;
    }
    @Override
    public List<for_help> downLoadFor_help(int fid){
        List<for_help> temp =  jdbcTemplate.query("select fid,title,content,ending_time,pay from moneydog.for_help where fid <= ? limited 15 order by fid desc; ",new Object[]{fid},new BeanPropertyRowMapper(for_help.class));
        return  temp;
    }
    @Override
    public List<second_hand> downLoadSecond_hand(int sid){
        List<second_hand> temp =  jdbcTemplate.query("select sid,object_name,content,pay,ending_time from moneydog.second_hand where sid <= ? limited 15 order by size desc;",new Object[]{sid},new BeanPropertyRowMapper(second_hand.class));
        return  temp;
    }
    @Override
    public List<expressage> OnLoadExpressage(){
        // 获取最大的id，即最新的消息id，找出15条返回
        int id = jdbcTemplate.queryForObject("SELECT COUNT(pid) AS NumberOfProducts FROM expressage;",int.class);
        List<expressage> temp =  jdbcTemplate.query("select express_loc,arrive_time,num,pay,loc,pid from moneydog.expressage where pid <= ? limited 15 order by pid DESC; ",new Object[]{id},new BeanPropertyRowMapper(expressage.class));
        return  temp;
    }
    @Override
    public List<errand> OnLoadErrands(){
        int id = jdbcTemplate.queryForObject("SELECT COUNT(rid) AS NumberOfProducts FROM errand;",int.class);
        List<errand> temp =  jdbcTemplate.query("select rid,title,content,pay,ending_time from moneydog.errand where rid <= ? limited 15 order by rid desc; ",new Object[]{id},new BeanPropertyRowMapper(errand.class));
        return  temp;
    }
    @Override
    public List<for_help> OnLoadFor_help(){
        int id = jdbcTemplate.queryForObject("SELECT COUNT(fid) AS NumberOfProducts FROM for_help;",int.class);
        List<for_help> temp =  jdbcTemplate.query("select fid,title,content,ending_time,pay from moneydog.for_help where fid <= ? limited 15 order by fid desc;",new Object[]{id},new BeanPropertyRowMapper(for_help.class));
        return  temp;
    }
    @Override
    public List<second_hand> OnLoadSecond_hand(){
        int id = jdbcTemplate.queryForObject("SELECT COUNT(sid) AS NumberOfProducts FROM second_hand;",int.class);
        List<second_hand> temp =  jdbcTemplate.query("select sid,object_name,content,pay,ending_time from moneydog.second_hand where sid <= ? limited 15 order by size desc;",new Object[]{id},new BeanPropertyRowMapper(second_hand.class));
        return  temp;
    }
}
