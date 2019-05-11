package backend1.demo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/Modified")
@RestController
public class AcceptController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @RequestMapping(value = "/AcceptIssue",method = RequestMethod.PUT)
    public JSONObject AcceptIssue(@RequestHeader("sessionId")String sessionId, @RequestParam("type")int type,@RequestParam("id")int id)
    {
        String key =  stringRedisTemplate.opsForValue().get(sessionId);
        JSONObject temp = (JSONObject)JSON.parse(key);
        String openid = temp.getString("openid");
        String sql = "";

        switch (type){
            case 0:sql = "update moneydog.expressage set uid2 = ?,state = 1 where pid = ? ";break;//快递
            case 1:sql = "update moneydog.errand set uid2 = ?,state = 1 where rid = ? ";break;//跑腿
            case 2:sql = "update moneydog.for_help set uid2 = ?,state = 1 where fid = ? ";break;//求助
            case 3:sql = "update moneydog.second_hand set uid2 = ?,state = 1 where sid = ? ";break;//二手
            default:break;
        }
        int errcode = jdbcTemplate.update(sql,openid,id);
        JSONObject jt = new JSONObject();
        String errmsg;
        if(errcode == 1){
            errmsg = "Accept succeessfully";
        }
        else {
            errmsg = "Accept failed";
        }
        jt.put("errcode",errcode);
        jt.put("errmsg",errmsg);
        return jt;
    }

    @RequestMapping(value = "/DeleteIssue",method = RequestMethod.PUT)
    public JSONObject DeleteIssue(@RequestHeader("sessionId")String sessionId,@RequestParam("type") int type,@RequestParam("id") int id){
        String key =  stringRedisTemplate.opsForValue().get(sessionId);
        JSONObject temp = (JSONObject)JSON.parse(key);
        String openid = temp.getString("openid");

        String sql = "";

        switch (type){
            case 0:sql = "update moneydog.expressage set state = 2 where uid1 = ? and pid = ?";break;//快递
            case 1:sql = "update moneydog.errand set state = 2 where  uid1 = ? and rid = ? ";break;//跑腿
            case 2:sql = "update moneydog.for_help set state = 2 where uid1 = ? and fid = ? ";break;//求助
            case 3:sql = "update moneydog.second_hand set state = 2 where uid1 = ? and sid = ? ";break;//二手
            default:break;
        }

        JSONObject jt = new JSONObject();
        String errmsg;
        int errcode;
        try{
            errcode = jdbcTemplate.update(sql,openid,id);
            if(errcode == 1){
                errmsg = "Accept succeessfully";
            }
            else {
                errmsg = "Accept failed";
            }
        }catch (Exception e){
            errcode = 0;
            errmsg = "Accept failed";
        }
        jt.put("errcode",errcode);
        jt.put("errmsg",errmsg);
        return jt;
    }
}
