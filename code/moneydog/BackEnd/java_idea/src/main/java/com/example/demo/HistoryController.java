package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


@RequestMapping("/History")
@Api(description = "管理余额明细记录")
@RestController
public class HistoryController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "创建余额明细记录")
    @PostMapping("/createHistory")
    public JSONObject CreateHistory(@ApiParam(required = true, value = "创建订单者的openid")@RequestParam("uid") String uid,
                                    @ApiParam(required = true, value = "订单的类型")@RequestParam("type") String type,
                                    @ApiParam(required = true, value = "订单产生的交易金额")@RequestParam("price") int price) {
        Timestamp d = new Timestamp(System.currentTimeMillis());
        JSONObject result = new JSONObject();
        String msg;
        int statecode;

        //detail为备注，需要写得比较清楚
        String thid  = UUID.randomUUID().toString() ;
        String sub = "subtract";
        String add = "add";
        String openid = uid;
        String sql = "SELECT school,phoneNum,falsename,realname FROM moneydog.user WHERE openid = ?";
        List<User> temp1  = jdbcTemplate.query(sql,new Object[] {openid},new BeanPropertyRowMapper(User.class));
        if(temp1.size() == 0)
        {
            statecode = -1;
            msg = " 用户不存在";
        }
        else if(!isRich(openid,-price))
        {
            statecode = -1;
            msg = "余额不足";
        }
        else
        {
            statecode = 1;
            msg = "创建对应的历史订单记录成功";
            User temp = temp1.get(0);
            String detail = temp.getFalsename() + "创建了" +  type + "订单支付了" + price + "闲钱币";
            jdbcTemplate.update("insert into moneydog.history(thid,uid,type,price,detail,time,finish)values (?,?,?,?,?,?,?)", thid,uid,sub,price,detail,d,0);
            changeBalance(uid,-price);
        }


        result.put("msg",msg);
        result.put("thid",thid);
        result.put("statecode",statecode);
        return result;
    }

    @ApiOperation(value = "完成余额明细记录")
    @PostMapping("/finishHistory")
    public JSONObject finishHistory(@ApiParam(required = true, value = "余额明细的thid")@RequestParam("thid") String thid,
                                    @ApiParam(required = true, value = "接单者的openid")@RequestParam("uid") String uid) {
        JSONObject result = new JSONObject();
        //状态码 -1失败 1成功
        int statecode = 1;
        String msg;

        Timestamp d = new Timestamp(System.currentTimeMillis());
        //detail为备注，需要写得比较清楚
        //String thid  = UUID.randomUUID().toString() ;
        String openid = uid;
        String sql = "SELECT school,phoneNum,falsename,realname FROM moneydog.user WHERE openid = ?";
        List<User> temp1  = jdbcTemplate.query(sql,new Object[] {openid},new BeanPropertyRowMapper(User.class));
        String sql1 = "SELECT price,uid,detail,finish FROM moneydog.history WHERE thid = ?";
        List<History> temp2  = jdbcTemplate.query(sql1,new Object[] {thid},new BeanPropertyRowMapper(History.class));

        if(temp1.size() == 0)
        {
            statecode = -1;
            msg = "用户不存在";
        }
        else if(temp2.size() == 0)
        {
            statecode = -1;
            msg = "订单不存在";
        }
        else if(temp2.get(0).getFinish() == 1)
        {
            statecode = -1;
            msg = "订单已完成，请勿重复完成";
        }
        else
        {
            User user = temp1.get(0);//temp为接单者
            History history = temp2.get(0);
            String detail = history.getDetail() + "给" +  user.getFalsename();
            jdbcTemplate.update("insert into moneydog.history(thid,uid,type,price,detail,time,finish,uid2)values (?,?,?,?,?,?,?,?)",
                    UUID.randomUUID().toString(),uid,"add",history.getPrice(),detail,d,1,history.getUid());

            jdbcTemplate.update("UPDATE moneydog.history set uid2 = ? , detail = ?,time = ? , finish = ? WHERE thid = ? ",
                    uid,detail,d,1,thid);
            statecode = 1;
            changeBalance(uid,history.getPrice());
            msg = "成功修改";
        }
        result.put("statecode",statecode);
        result.put("msg",msg);


        return result;
    }


    @ApiOperation(value = "获取余额明细")
    @RequestMapping(method = RequestMethod.GET, value = "/getHistory")
    public JSONObject getHistory(@ApiParam(required = true, value = "用户特征值")@RequestHeader("sessionId")String sessionId)
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
    public boolean isRich(String openid,int power)
    {
        int count;
        String sql = "SELECT balance FROM moneydog.user WHERE openid = ?";
        //System.out.println("openid: " + openid);
        count = jdbcTemplate.queryForObject(sql,new Object[] {openid},Integer.class);
        count += power;
        return count >= 0;
    }
    public int changeBalance(String openid,int power)
    {
        int count;
        String sql = "SELECT balance FROM moneydog.user WHERE openid = ?";
        //System.out.println("openid: " + openid);
        count = jdbcTemplate.queryForObject(sql,new Object[] {openid},Integer.class);
        count += power;
        return jdbcTemplate.update("UPDATE moneydog.user set balance = ?  WHERE openid = ? ",count,openid);
    }
}
