package backend1.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/Load")
@RestController
public class LoadController {

    @Autowired
    private LoadSerivce loadSerivce;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(method = RequestMethod.GET,value = "/Contact_way")
    public List<Contact> getContact(@RequestParam("type") int type,@RequestParam("id")int id){
        String sql = "";
        switch (type){
            case 0:sql = "select phone,wechat from moneydog.expressage where pid = ? ";break;//快递
            case 1:sql = "select phone,wechat from moneydog.errand where rid = ? ";break;//跑腿
            case 2:sql = "select phone,wechat from moneydog.for_help where fid = ? ";break;//求助
            case 3:sql = "select phone,wechat from moneydog.second_hand where sid = ? ";break;//二手
        }
        return jdbcTemplate.query(sql,new Object[]{id},new BeanPropertyRowMapper(Contact.class));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/downLoadExpressage")
    public List<expressage> downLoadExpressage(@RequestParam("id") int pid) {
        return loadSerivce.downLoadExpressage(pid);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/downLoadErrands")
    public List<errand> downLoadErrands(@RequestParam("id") int rid) {
        return loadSerivce.downLoadErrands(rid);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/downLoadFor_help")
    public List<for_help> downLoadFor_help(@RequestParam("id") int fid) {
        return loadSerivce.downLoadFor_help(fid);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/downLoadSecond_hand")
    public List<second_hand> downLoadSecond_hand(@RequestParam("id") int sid) {
        return loadSerivce.downLoadSecond_hand(sid);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/OnLoadExpressage")
    public List<expressage> OnLoadExpressage() {
        return loadSerivce.OnLoadExpressage();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/OnLoadErrands")
    public List<errand> OnLoadErrands() {
        return loadSerivce.OnLoadErrands();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/OnLoadFor_help")
    public List<for_help> OnLoadFor_help() {
        return loadSerivce.OnLoadFor_help();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/OnLoadSecond_hand")
    public List<second_hand> OnLoadSecond_hand() {
        return loadSerivce.OnLoadSecond_hand();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/Creation")
    JSONObject LoadMyCreation( @RequestHeader("sessionId") String sessionId) {
        String temp = stringRedisTemplate.opsForValue().get(sessionId);
        JSONObject jsonObject = (JSONObject)JSON.parse(temp);
        String openid = jsonObject.getString("openid");
        return loadSerivce.LoadMyCreation(openid);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/Receiving")
    JSONObject LoadMyReceiving(@RequestHeader("sessionId") String sessionId) {
        String temp = stringRedisTemplate.opsForValue().get(sessionId);
        JSONObject jsonObject = (JSONObject)JSON.parse(temp);
        String openid = jsonObject.getString("openid");
        return loadSerivce.LoadMyReceiving(openid);
    }

}
