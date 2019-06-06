package backend1.demo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    private JSONObject CreateIssueBalanceDetail(String thid,String uid){

        //请求路径
        String url = "https://moneydog.club:3336/History/finishHistory";

        RestTemplate restTemplate = new RestTemplate();

        LinkedMultiValueMap body=new LinkedMultiValueMap();
        body.add("thid",thid);
        body.add("uid",uid);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity httpEntity = new HttpEntity(body,headers);
        try {
            ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST,httpEntity,String.class);
            JSONObject jsTemp = JSONObject.parseObject(strbody.getBody());
            return jsTemp;

        }catch (Exception e){
            System.out.println(e);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("statecode",0);
        jsonObject.put("msg","请求错误,稍后再试");
        return  jsonObject;
    }

    @RequestMapping(value = "/FinishIssue",method = RequestMethod.PUT)
    public JSONObject FinishIssue(@RequestHeader("sessionId") String sessionId,@RequestParam("type")int type,@RequestParam("id")int id){
        String key =  stringRedisTemplate.opsForValue().get(sessionId);
        JSONObject temp = (JSONObject)JSON.parse(key);
        String openid = temp.getString("openid");

        String sql = "";
        String thid_select = "";

        switch (type){
            case 0:sql = "update moneydog.expressage set state = 2 where uid1 = ? and pid = ?";break;//快递
            case 1:sql = "update moneydog.errand set state = 2 where  uid1 = ? and rid = ? ";break;//跑腿
            case 2:sql = "update moneydog.for_help set state = 2 where uid1 = ? and fid = ? ";break;//求助
            case 3:sql = "update moneydog.second_hand set state = 2 where uid1 = ? and sid = ? ";break;//二手
            default:break;
        }
        switch (type){
            case 0:thid_select = "select thid from moneydog.expressage where uid1 = ? and pid = ?";break;//快递
            case 1:thid_select = "select thid from moneydog.errand where  uid1 = ? and rid = ? ";break;//跑腿
            case 2:thid_select = "select thid from moneydog.for_help where uid1 = ? and fid = ? ";break;//求助
            case 3:thid_select = "select thid from moneydog.second_hand where uid1 = ? and sid = ? ";break;//二手
            default:break;
        }
        String thid = "";
        try {
            thid = jdbcTemplate.queryForObject(thid_select,new Object[]{openid,id},String.class);
            CreateIssueBalanceDetail(thid,openid);

        }catch (Exception e){
            System.out.println("完成余额错误");
            System.out.println(e);
        }


        JSONObject jt = new JSONObject();
        String errmsg;
        int errcode;
        try{
            errcode = jdbcTemplate.update(sql,openid,id);
            if(errcode == 1){
                errmsg = "Finish succeessfully";
            }
            else {
                errmsg = "Finish failed";
            }
        }catch (Exception e){
            errcode = 0;
            errmsg = "Finish failed";
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
            case 0:sql = "update moneydog.expressage set state = 6 where uid1 = ? and pid = ? and state != 1;";break;//快递
            case 1:sql = "update moneydog.errand set state = 6 where  uid1 = ? and rid = ?  and state != 1;";break;//跑腿
            case 2:sql = "update moneydog.for_help set state = 6 where uid1 = ? and fid = ? and state != 1; ";break;//求助
            case 3:sql = "update moneydog.second_hand set state = 6 where uid1 = ? and sid = ? and state != 1; ";break;//二手
            default:break;
        }

        JSONObject jt = new JSONObject();
        String errmsg;
        int errcode;
        try{
            errcode = jdbcTemplate.update(sql,openid,id);
            if(errcode == 1){
                errmsg = "Delete succeessfully";
            }
            else {
                errmsg = "Delete failed";
            }
        }catch (Exception e){
            errcode = 0;
            errmsg = "Delete failed";
        }
        jt.put("errcode",errcode);
        jt.put("errmsg",errmsg);
        return jt;
    }
}
