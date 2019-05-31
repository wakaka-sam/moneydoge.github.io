package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RequestMapping("/LoveWall")
@Api(description = "表白墙管理")
@RestController
public class LoveWallController {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @ApiOperation(value = "创建表白墙")
    @PostMapping("/createWall")
    public JSONObject CreateHistory(@ApiParam(required = true, value = "被表白者的名字")@RequestParam("toPeople") String toPeople,
                                    @ApiParam(required = true, value = "表白内容")@RequestParam("detail") String detail)
    {
        Timestamp d = new Timestamp(System.currentTimeMillis());
        JSONObject result = new JSONObject();
        String msg;
        int statecode;
        jdbcTemplate.update("insert into moneydog.loveWall(detail,toPeople,registe_time)values (?,?,?)", detail,toPeople,d);
        statecode = 1;
        msg = "创建成功";
        result.put("msg",msg);
        result.put("statecode",statecode);
        return result;
    }

    @ApiOperation(value = "获取表白墙")
    @RequestMapping(method = RequestMethod.GET, value = "/getLoveWall")
    public JSONObject getHistory()
    {
        int statecode;
        String msg;
        JSONObject result = new JSONObject();
        String sql = "SELECT lwid,detail,toPeople,likeNum FROM moneydog.loveWall ORDER BY registe_time DESC ";
        List<LoveWall> temp1  = jdbcTemplate.query(sql,new BeanPropertyRowMapper(LoveWall.class));
        if(temp1.isEmpty())
        {
            statecode = -1;
            msg = "表白墙内容为空";
        }
        else
        {
            statecode = 1;
            msg = "获取成功";
        }
        result.put("statecode",statecode);
        result.put("msg",msg);
        result.put("data",temp1);
        return result;

    }

    @ApiOperation(value = "表白墙点赞")
    @PostMapping("/likeLoveWall")
    public JSONObject likeLoveWall(@ApiParam(required = true, value = "表白墙id")@RequestParam("lwid") int lwid)
    {
        int statecode;
        String msg;
        JSONObject result = new JSONObject();
        int likeNum;
        try{
            String sql = "SELECT likeNum FROM moneydog.loveWall WHERE lwid = ?";
            likeNum = jdbcTemplate.queryForObject(sql,new Object[] {lwid},Integer.class);

        }
        catch (Exception e)
        {
            statecode = -1;
            msg = "用户不存在";
            result.put("msg",msg);
            result.put("statecode",statecode);

            return result;
        }
        likeNum += 1;
        jdbcTemplate.update("UPDATE moneydog.loveWall set likeNum = ?  WHERE lwid = ? ",likeNum,lwid);
        statecode = 1;
        msg = "点赞成功";
        result.put("msg",msg);
        result.put("statecode",statecode);

        return result;
    }
}
