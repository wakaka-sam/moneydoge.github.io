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

import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(description = "管理用户相关信息")
@RequestMapping("/User")
@RestController
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //完善信息，并改变state值
    @ApiOperation(value = "完善信息")
    @PostMapping("/Update")
    public int updateinfo(@ApiParam(required = true, value = "用户昵称")@RequestParam("nickname") String falsename,
                          @ApiParam(required = true, value = "用户真实姓名")@RequestParam("name") String realname,
                          @ApiParam(required = true, value = "用户学校")@RequestParam("school") String school,
                          @ApiParam(required = true, value = "用户电话号码")@RequestParam("phone") String phoneNum,
                          @ApiParam(required = true, value = "用户校园卡图像url")@RequestParam("image_url") String image_url,
                          @ApiParam(required = true, value = "用户头像url")@RequestParam("user_img") String user_img,
                          @ApiParam(required = true, value = "用户特征值")@RequestHeader("sessionId")String sessionId) {
        //state的1代表信息完善
        String openid = getOpenidBySessionId(sessionId);
        int state = 0;
        /*
        if(falsename.isEmpty()||realname.isEmpty()||school.isEmpty()||phoneNum.isEmpty())
            state = 0;
        else
            state = 1;

         */
        System.out.println("用户" + sessionId + "调用了Update");
        return jdbcTemplate.update("UPDATE moneydog.user set falsename = ?,realname = ?,school = ?,phoneNum = ?, state = ?,image_url = ?,avatarUrl = ?  WHERE openid = ? ",
                falsename,realname,school,phoneNum,state,image_url,user_img,openid);
    }

    //获得闲币
    @ApiOperation(value = "设置闲钱币")
    @PostMapping("/setPower")
    public int setPower(@ApiParam(required = true, value = "用户特征值")@RequestHeader("sessionId")String sessionId,
                        @ApiParam(required = true, value = "需要设置的闲钱币的值")@RequestParam("power") int power)
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


    //查询闲币
    @ApiOperation(value = "查询闲钱币")
    @RequestMapping(method = RequestMethod.GET, value = "/queryPower")
    public int queryPower(@ApiParam(required = true, value = "用户特征值")@RequestHeader("sessionId")String sessionId)
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
    @Api(description = "生成测试用sessionid")
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
    @ApiOperation(value = "获取用户资料",notes = "返回每个用户的昵称，手机号，真实姓名以及学校",response = User.class)
    @RequestMapping(method = RequestMethod.GET, value = "/Info")
    public User getInfo(@ApiParam(required = true, value = "用户特征值")@RequestHeader("sessionId")String sessionId) {

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


    @ApiOperation(value = "更改其认证状态")
    @PostMapping("/changeState")
    public JSONObject changeState(@ApiParam(required = true, value = "用户特征值openid")@RequestParam("openid")String openid,
                @ApiParam(required = true, value = "认证状态值,-1不通过，0未认证，1已认证")@RequestParam("state") int state) {

        //String openid = getOpenidBySessionId(sessionId);
        int statecode;
        String msg;

        try{
            jdbcTemplate.update("UPDATE moneydog.user set state = ?  WHERE openid = ? ",state,openid);

        }
        catch (Exception e)
        {
            statecode = -1;
            msg = "用户不存在";
            JSONObject result = new JSONObject();
            result.put("msg",msg);
            result.put("statecode",statecode);

            return result;
        }

        statecode = 1;
        msg = "修改成功";

        JSONObject result = new JSONObject();
        result.put("msg",msg);
        result.put("statecode",statecode);

        return result;

    }

    @ApiOperation(value = "获取未认证的用户")
    @RequestMapping(method = RequestMethod.GET, value = "/getUnstated")
    public JSONObject  getUnstatedList() {

        JSONObject result = new JSONObject();
        int statecode;
        String msg;
        String sql = "SELECT openid,image_url,state,realname FROM moneydog.user";
        List<SchoolCard> temp1  = jdbcTemplate.query(sql,new BeanPropertyRowMapper(SchoolCard.class));
        //System.out.println("用户" + sessionId + "调用了getInfo");
        //return temp1.get(0);

        if(temp1.isEmpty())
        {
            statecode = -1;
            msg = "无未认证用户";
        }
        else
        {
            statecode = 1;
            msg = "获取成功";
        }
        result.put("user",temp1);
        result.put("statecode",statecode);
        result.put("msg",msg);
        return result;
    }


    //提供给另一后端
    /*
    @PostMapping("/changePower")
    public JSONObject changePower(@RequestParam("openid")String uid,@RequestParam("power") int power)
    {
        //1 交易成功  2 余额不足
        // -1 账号不存在
        int statecode;
        String msg;
        String openid = uid;
        int count ;
        String sql = "SELECT balance FROM moneydog.user WHERE openid = ?";
        //System.out.println("openid: " + openid);
        try
        {
            count = jdbcTemplate.queryForObject(sql,new Object[] {openid},Integer.class);

        }
        catch (Exception e)
        {
            statecode = -1;
            JSONObject temp = new JSONObject();
            temp.put("statecode",statecode);
            temp.put("msg","账号不存在");
            temp.put("count",0);
            return temp;
        }
        count += power;

        if(count < 0)
        {
            statecode = 2;
            msg = "余额不足";
            System.out.println("用户" + uid + "调用了changePower" + " 余额不足");
        }
        else
        {
            statecode = 1;
            jdbcTemplate.update("UPDATE moneydog.user set balance = ?  WHERE openid = ? ",count,openid);
            System.out.println("用户" + uid + "调用了changePower" + " 交易成功");
            msg = "交易成功";
        }
        JSONObject temp = new JSONObject();
        temp.put("statecode",statecode);
        temp.put("count",count);
        temp.put("msg",msg);
        return temp;
    }


     */



}
