package backend1.demo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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


    private JSONObject GetOpenId(String code) {
        //得到完整的
        //{
        // errcode,
        // errmsg,
        // openid
        // session_key
        // }
        RestTemplate restTemplate = new RestTemplate();
        String appid = "wx08dea5e778f278de&";
        String secret = "77fc034ff68fe7799e4e8723466a50d7&";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid
                + "&secret=" + secret
                + "&js_code=" + code
                + "&grant_type=authorization_code";
        JSONObject jsonObject = new JSONObject();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String strbody = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        JSONObject res = JSONObject.parseObject(strbody, JSONObject.class);
        int errcode = 0;
        String errmsg = "";
        try {
            errcode = res.getInteger("errcode");
            errmsg = res.getString("errmsg");
            //若出错，则仅返回errcode
            if (errcode != 0) {
                jsonObject.put("errcode", errcode);
                jsonObject.put("errmsg", errmsg);
                return jsonObject;
            }
        } catch (Exception e) {
        }
        String openid = "";
        String session_key = "";
        try {
            openid = res.getString("openid");
            session_key = res.getString("session_key");

        } catch (Exception e) {
        }
        jsonObject.put("errcode", errcode);
        jsonObject.put("errmsg", errmsg);
        jsonObject.put("openid", openid);
        jsonObject.put("session_key", session_key);
        return jsonObject;
    }

    private JSONObject setSessionId(JSONObject res) {
        JSONObject RedisSession = new JSONObject();

        //用json存储
        String openid = res.getString("openid");
        String session_key = res.getString("session_key");
        RedisSession.put("openid", openid);
        RedisSession.put("session_key", session_key);

        if (createService.Login(openid) == 1) {
            //生成sessionId
            String SessionId = UUID.randomUUID().toString();
            //存入redis
            stringRedisTemplate.opsForValue().set(SessionId, RedisSession.toString(), 60, TimeUnit.MINUTES);

            //返回sessionId
            JSONObject time = new JSONObject();
            time.put("SessionId", SessionId);
            time.put("expireTime", 60);
            return time;
        }
        return null;

    }

    @PostMapping("/User")
    public JSONObject CreateUser(@RequestParam(value = "nickName") String nickName, @RequestParam(value = "code") String code, @RequestParam(value = "avatarUrl") String avatarUrl, @RequestParam(value = "gender") String gender) {
//        code = "081HSn7U0N9ZA02TG44U0K157U0HSn7M"
        System.out.println(code);
        JSONObject res = GetOpenId(code);
        System.out.println(res);
        if (res.getInteger("errcode") != 0)
            return res;

        String openid = res.getString("openid");
        String session_key = res.getString("session_key");

        int t = createService.CreateUser(openid, nickName, avatarUrl, gender);
        if (t <= 0) {
            res.put("errcode", -1);
            res.put("errmsg", "create user failed");
            return res;
        }
        JSONObject session = setSessionId(res);
        res.put("SessionId", session.getString("SessionId"));
        res.put("expireTime", session.getInteger("expireTime"));
        return res;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/Login")
    public JSONObject Login(@RequestParam("code") String code) {

        JSONObject res = GetOpenId(code);
        if (res.getInteger("errcode") != 0)
            return res;

        JSONObject session = setSessionId(res);
        //retrn SessionId
        return session;

    }

    private String getOpenidFromSession(String sessionId){
        String key =  stringRedisTemplate.opsForValue().get(sessionId);
        JSONObject temp = (JSONObject) JSON.parse(key);
        String openid = temp.getString("openid");
        return openid;
    }
    @PostMapping("/Expressage")
    public JSONObject CreateExpressage(@RequestHeader("sessionId")String sessionId,@RequestParam("express_loc") String express_loc, @RequestParam("arrive_time") Date arrive_time, @RequestParam("loc") String loc, @RequestParam("num") int num, @RequestParam("pay") int pay, @RequestParam("remark") String remark, @RequestParam("phone") String phone, @RequestParam("wechat") String wechat) {
        String openid = getOpenidFromSession(sessionId);
        return createService.CreateExpressage(openid,express_loc, arrive_time, loc, num, pay, remark, phone, wechat);
    }

    @ResponseBody
    @RequestMapping(value = "/For_help", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject CreateFor_help(@RequestHeader("sessionId")String sessionId,@RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("phone") String phone, @RequestParam("wechat") String wechat, @RequestParam("ending_time") Date ending_time, @RequestParam("pay") int pay) {
        String openid = getOpenidFromSession(sessionId);
        return createService.CreateFor_help(openid,title, content, phone, wechat, ending_time, pay);
    }

    @PostMapping("/Errand")
    public JSONObject CreateErrand(@RequestHeader("sessionId")String sessionId,@RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("phone") String phone, @RequestParam("wechat") String wechat, @RequestParam("ending_time") Date ending_time, @RequestParam("pay") int pay) {
        String openid = getOpenidFromSession(sessionId);
        return createService.CreateErrand(openid,title, content, phone, wechat, ending_time, pay);
    }
//        @PostMapping("/Errand")
//        public JSONObject CreateErrand(@RequestBody String title, @RequestBody String content, @RequestBody String phone, @RequestBody String wechat, @RequestBody Date ending_time, @RequestBody int pay) {
//            return createService.CreateErrand(title, content, phone, wechat, ending_time, pay);
//        }

    @PostMapping("/Second_hand")
    public JSONObject CreateSecond_hand(@RequestHeader("sessionId")String sessionId,@RequestParam("object_name") String object_name, @RequestParam("content") String content, @RequestParam("phone") String phone, @RequestParam("wechat") String wechat, @RequestParam("ending_time") Date ending_time, @RequestParam("pay") int pay, @RequestParam("photo_url") String photo_url) {
        String openid = getOpenidFromSession(sessionId);
        return createService.CreateSecond_hand(openid,object_name, content, phone, wechat, ending_time, pay, photo_url);
    }
}
