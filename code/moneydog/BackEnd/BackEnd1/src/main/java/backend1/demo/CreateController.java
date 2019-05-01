package backend1.demo;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequestMapping("/Create")
@RestController
public class CreateController {
    @Autowired
    private CreateService createService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/User")
    public JSONObject CreateUser(@RequestParam("code") String code,@RequestParam("nickName") String nickName,@RequestParam("avatarUrl") String avatarUrl,@RequestParam("gender") String gender){
        RestTemplate restTemplate = new RestTemplate();
        String appid = "wx08dea5e778f278de&";
        String secret = "77fc034ff68fe7799e4e8723466a50d7&";
        String Url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid
                +"&secret="+ secret
                + "&js_code=" + code
                + "&grant_type=authorization_code";
        JSONObject jsonObject = new JSONObject();

        JSONObject res = restTemplate.getForObject("",JSONObject.class);
        // res{
        //      openid,
        //      session_key,
        //      unionid,
        //      errcode,
        //      errmsg
        //  }
        int errcode = res.getInteger("errcode");
        if(errcode != 0){
            //若出错，则仅返回errcode
            jsonObject.put("errcode",errcode);
            jsonObject.put("errmsg",res.getString("errmsg"));
            return jsonObject;
        }
        String openid = res.getString("openid");
        String session_key = res.getString("session_key");
        //注册部分：
        int t = createService.CreateUser(openid,nickName,avatarUrl,gender);
        if(t <= 0){
            jsonObject.put("errcode",-1);
            jsonObject.put("errmsg","create uer failed");
            return  jsonObject;
        }
        //生成SessionId
        String SessionId = UUID.randomUUID().toString();
        jsonObject.put("errcode",errcode);
        jsonObject.put("errmsg",res.getString("errmsg"));
        jsonObject.put("SessionId",SessionId);
        jsonObject.put("expireTime",60);


        JSONObject RedisSession = new JSONObject();
        RedisSession.put("openid",openid);
        RedisSession.put("session_key",session_key);
        //存入Redis
        stringRedisTemplate.opsForValue().set(SessionId,RedisSession.toString(),60, TimeUnit.MINUTES);
        return  jsonObject;//retrn SessionId
    }

    @PostMapping("/Expressage")
    public int CreateExpressage(@RequestParam("express_loc") String express_loc,@RequestParam("arrive_time") Date arrive_time,@RequestParam("loc") String loc,@RequestParam("num") int num,@RequestParam("pay") int pay,@RequestParam("remark") String remark,@RequestParam("phone") String phone,@RequestParam("wechat") String wechat){
        return   createService.CreateExpressage(express_loc,arrive_time,loc,num,pay,remark,phone,wechat);
    }

    @ResponseBody
    @RequestMapping(value = "/For_help",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public int CreateFor_help(@RequestParam("title") String title,@RequestParam ("phone") String phone,@RequestParam("wechat") String wechat,@RequestParam("ending_time") Date ending_time,@RequestParam("pay") int pay){
        return createService.CreateFor_help(title,phone,wechat,ending_time,pay);
    }
    @PostMapping("/Errand")
    public  int CreateErrand(@RequestParam("title") String title,@RequestParam("phone") String phone,@RequestParam("wechat") String wechat,@RequestParam("ending_time") Date ending_time,@RequestParam("pay") int pay){
        return createService.CreateErrand(title,phone,wechat,ending_time,pay);
    }
    @PostMapping("/Second_hand")
    public  int CreateSecond_hand(@RequestParam("object_name") String object_name,@RequestParam("phone") String phone,@RequestParam("wechat") String wechat,@RequestParam("ending_time") Date ending_time,@RequestParam("pay") int pay,@RequestParam("photo_url") String photo_url){
        return createService.CreateSecond_hand(object_name,phone,wechat,ending_time,pay,photo_url);
    }
}
