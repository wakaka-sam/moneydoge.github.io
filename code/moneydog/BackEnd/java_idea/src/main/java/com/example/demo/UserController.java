package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;


@RequestMapping("/User")
@RestController
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //完善信息，并改变state值
    @PostMapping("/Update")
    public int updateinfo(@RequestParam("nickname") String falsename, @RequestParam("name") String realname,
                          @RequestParam("school") String school,@RequestParam("phone") String phoneNum,
                          @RequestParam("image_url") String image_url,@RequestParam("user_img") String user_img,
                          @RequestHeader("sessionId")String sessionId) {
        //若有空值，则state为0，否则为1
        //state的1代表信息完善
        String openid = getOpenidBySessionId(sessionId);
        int state ;
        if(falsename.isEmpty()||realname.isEmpty()||school.isEmpty()||phoneNum.isEmpty())
            state = 0;
        else
            state = 1;
        System.out.println("用户" + sessionId + "调用了Update");
        return jdbcTemplate.update("UPDATE moneydog.user set falsename = ?,realname = ?,school = ?,phoneNum = ?, state = ?,image_url = ?,avatarUrl = ?  WHERE openid = ? ",
                falsename,realname,school,phoneNum,state,image_url,user_img,openid);
    }

    //获得闲币
    @PostMapping("/setPower")
    public int setPower(@RequestHeader("sessionId")String sessionId,@RequestParam("power") int power)
    {

        String openid = getOpenidBySessionId(sessionId);

        //String sql = "SELECT balance FROM moneydog.user WHERE openid = ?";
        //System.out.println("openid: " + openid);
        //count = jdbcTemplate.queryForObject(sql,new Object[] {openid},Integer.class);
        //count += power;
        jdbcTemplate.update("UPDATE moneydog.user set balance = ?  WHERE openid = ? ",power,openid);
        System.out.println("用户" + sessionId + "调用了setPower" + "返回了" + power);

        return power;
    }
    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String sayHello()
    {
        return "Hello";
    }

    //查询闲币
    @RequestMapping(method = RequestMethod.GET, value = "/queryPower")
    public int queryPower(@RequestHeader("sessionId")String sessionId)
    {
        String openid = getOpenidBySessionId(sessionId);
        System.out.println("sessionId" + sessionId);
        System.out.println("openid" + openid);
        int count = 1;
        String sql = "SELECT balance FROM moneydog.user WHERE openid = ?";
        count = jdbcTemplate.queryForObject(sql,new Object[] {openid},Integer.class);
        System.out.println("用户" + sessionId + "调用了queryPower返回了" + count);
        /*
        JSONObject temp = new JSONObject();
        temp.put("count",count);
        temp.put("msg","success");

         */
        return count;
    }

    @RequestMapping("/forever")
    @RestController
    public class createid {
        @RequestMapping(value = "/cc",method = RequestMethod.GET)
        public String foreverSession(){
//        "openid":"oXiVH42Q2C6Ub-xi2vlNMYkGz_dQ","session_key":"kBUPgEpdiaecDr7/6HJ7OQ=="
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("openid","oXiVH42Q2C6Ub-xi2vlNMYkGz_dQ");
            jsonObject.put("session_key","kBUPgEpdiaecDr7/6HJ7OQ==");
            String sessionId = "847694c4-14dd-47b2-8922-facd8e379f47";
            stringRedisTemplate.opsForValue().set(sessionId,jsonObject.toString(),1000, TimeUnit.DAYS);
            return  sessionId;
        }
    }
    //获取用户资料
    @RequestMapping(method = RequestMethod.GET, value = "/Info")
    public User getInfo(@RequestHeader("sessionId")String sessionId) {

        String openid = getOpenidBySessionId(sessionId);
        JSONObject userInfo = new JSONObject();
        String sql = "SELECT school,phoneNum,falsename,realname FROM moneydog.user WHERE openid = ?";
        List<User> temp1  = jdbcTemplate.query(sql,new Object[] {openid},new BeanPropertyRowMapper(User.class));
        System.out.println("用户" + sessionId + "调用了getInfo");
        return temp1.get(0);

    }
    public String getOpenidBySessionId(String sessionId)
    {
        String key =  stringRedisTemplate.opsForValue().get(sessionId);
        JSONObject temp = (JSONObject) JSON.parse(key);
        String openid = temp.getString("openid");
        return openid;
    }

}
