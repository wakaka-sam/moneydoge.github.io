package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.List;

@RequestMapping("/History")
@RestController
public class HistoryController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @PostMapping("/createHistory")
    public int CreateHistory(@RequestParam("uid") String uid, @RequestParam("type") String type, @RequestParam("price") int price, @RequestParam("detail") String detail) {
        Timestamp d = new Timestamp(System.currentTimeMillis());
        //detail为备注，需要写得比较清楚
        return jdbcTemplate.update("insert into moneydog.history(uid,type,price,detail,time)values (?,?,?,?,?)", uid,type,price,detail,d);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getHistory")
    public JSONObject getHistor(@RequestHeader("sessionId")String sessionId)
    {
        String openid = getOpenidBySessionId(sessionId);
        //JSONObject userInfo = new JSONObject();
        String sql = "SELECT type,price,time,detail FROM moneydog.history WHERE uid = ? ORDER BY time DESC ";
        List<History> temp1  = jdbcTemplate.query(sql,new Object[] {openid},new BeanPropertyRowMapper(History.class));
        System.out.println("用户" + sessionId + "调用了getHistory");
        JSONObject result = new JSONObject();
        int maxnum = 30;
        int size = temp1.size();
        boolean flag = false;
        if(size > maxnum)
        {
            size = maxnum;
            flag = true;
        }
        result.put("count",size);
        if(flag)
        {
            temp1 = temp1.subList(0,maxnum);
        }
        result.put("data",temp1);
        return result;
    }
    public String getOpenidBySessionId(String sessionId)
    {
        String key =  stringRedisTemplate.opsForValue().get(sessionId);
        JSONObject temp = (JSONObject) JSON.parse(key);
        String openid = temp.getString("openid");
        return openid;
    }

}
