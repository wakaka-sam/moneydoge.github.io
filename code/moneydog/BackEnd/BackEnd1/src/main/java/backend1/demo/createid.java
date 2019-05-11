package backend1.demo;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequestMapping("/forever")
@RestController
public class createid {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @RequestMapping(value = "/cc",method = RequestMethod.GET)
    public String foreverSession(){
//        "openid":"oXiVH42Q2C6Ub-xi2vlNMYkGz_dQ","session_key":"kBUPgEpdiaecDr7/6HJ7OQ=="
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openid","oXiVH42Q2C6Ub-xi2vlNMYkGz_dQ");
        jsonObject.put("session_key","kBUPgEpdiaecDr7/6HJ7OQ==");
        String sessionId = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(sessionId,jsonObject.toString(),1000, TimeUnit.DAYS);
        return  sessionId;
    }
}
