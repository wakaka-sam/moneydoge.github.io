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
import springfox.documentation.spring.web.json.Json;

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
        int errcode = 0;
        try{
            errcode = jdbcTemplate.update(sql,openid,id);
        }catch (Exception e){
            System.out.println(e);
        }
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
    private JSONObject CreateIssueBalanceDetail(int price,String detail,String uid){

//请求路径
        String url = "https://moneydog.club:3336/History/createHistory";
        //使用Restemplate来发送HTTP请求
        RestTemplate restTemplate = new RestTemplate();
        // json对象


        // LinkedMultiValueMap 有点像JSON，用于传递post数据，网络上其他教程都使用
        // MultiValueMpat<>来传递post数据
        // 但传递的数据类型有限，不能像这个这么灵活，可以传递多种不同数据类型的参数
        LinkedMultiValueMap body=new LinkedMultiValueMap();
        body.add("price",price);
        body.add("detail",detail);
        body.add("uid",uid);

        //设置请求header 为 APPLICATION_FORM_URLENCODED
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 请求体，包括请求数据 body 和 请求头 headers
        HttpEntity httpEntity = new HttpEntity(body,headers);
        try {
            //使用 exchange 发送请求，以String的类型接收返回的数据
            //ps，我请求的数据，其返回是一个json
            ResponseEntity<String> strbody = restTemplate.exchange(url,HttpMethod.POST,httpEntity,String.class);
            //解析返回的数据
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
        JSONObject jt = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        String sql = "";
        String thid_select = "";
        String detail ="";
        String select_uid2 = "";
        switch (type){
            case 0:sql = "update moneydog.expressage set state = 2 where uid1 = ? and pid = ?";detail = "完成快递订单，获得收入";break;//快递
            case 1:sql = "update moneydog.errand set state = 2 where  uid1 = ? and rid = ? ";detail = "完成快递订单，获得收入";break;//跑腿
            case 2:sql = "update moneydog.for_help set state = 2 where uid1 = ? and fid = ? ";detail = "完成快递订单，获得收入";break;//求助
            case 3:sql = "update moneydog.second_hand set state = 2 where uid1 = ? and sid = ? ";detail = "完成快递订单，获得收入";break;//二手
            default:break;
        }
        switch (type){
            case 0:thid_select = "select pay from moneydog.expressage where pid = ?";select_uid2 = "select uid2 from expressage where pid = ?";break;//快递
            case 1:thid_select = "select pay from moneydog.errand where rid = ? ";select_uid2 = "select uid2 from errand where rid = ?";break;//跑腿
            case 2:thid_select = "select pay from moneydog.for_help where fid = ? ";select_uid2 = "select uid2 from for_help where fid = ?";break;//求助
            case 3:thid_select = "select pay from moneydog.second_hand where sid = ? ";select_uid2 = "select uid2 from second_hand where sid = ?";break;//二手
            default:break;
        }
        boolean success = false;
        String uid2;
        try {

            uid2 = jdbcTemplate.queryForObject(select_uid2,new Object[]{id},String.class);
            int pay = jdbcTemplate.queryForObject(thid_select,new Object[]{id},int.class);
            if(type != 3){
//                int price = 0 - pay;
                int price = pay;
                JSONObject balance = CreateIssueBalanceDetail(price,detail,uid2);
                if(balance.getInteger("statecode")!= 1){
                    jsonObject.put("errcode",2);
                    jsonObject.put("errmsg","余额错误");
                    jsonObject.put("statecode",-1);
                    jsonObject.put("msg",balance.getString("msg"));
                    return  jsonObject;
                }

            }else {
                JSONObject TEMP1 = CreateIssueBalanceDetail(-pay,"购买闲置，付款",uid2);
                if(TEMP1.getInteger("statecode") == 1){
                    JSONObject TEMP2 = CreateIssueBalanceDetail(pay,"卖出闲置，获得收益",openid);
                    if(TEMP2.getInteger("statecode") == 1){
                        success = true;
                    }
                    System.out.println(TEMP2.getString("msg"));
                }
                System.out.println(TEMP1.getString("msg"));
                if(!success){
                    jsonObject.put("errcode",2);
                    jsonObject.put("errmsg","余额错误");
                    jsonObject.put("statecode",-1);
                    jsonObject.put("msg","余额出错");
                    return  jsonObject;
                }
            }
        }catch (Exception e){
            jsonObject.put("errcode",2);
            jsonObject.put("errmsg","余额错误");
            System.out.println("完成余额错误");
            System.out.println(e);
        }


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
        jsonObject.put("errcode",errcode);
        jsonObject.put("errmsg",errmsg);
        return jsonObject;
    }

    @RequestMapping(value = "/DeleteIssue",method = RequestMethod.PUT)
    public JSONObject DeleteIssue(@RequestHeader("sessionId")String sessionId,@RequestParam("type") int type,@RequestParam("id") int id){
        String key =  stringRedisTemplate.opsForValue().get(sessionId);
        JSONObject temp = (JSONObject)JSON.parse(key);
        String openid = temp.getString("openid");
        JSONObject jt = new JSONObject();
        String sql = "";
        String state = "";
        String paymoney = "";

        switch (type){
            case 0:sql = "update moneydog.expressage set state = 6 where uid1 = ? and pid = ? and state != 1;";state = "select state from expressage where pid = ?;";paymoney = "select pay from expressage where pid = ?;";break;//快递
            case 1:sql = "update moneydog.errand set state = 6 where  uid1 = ? and rid = ?  and state != 1;";state = "select state from errand where rid = ?;";paymoney = "select pay from expressage where pid = ?;";break;//跑腿
            case 2:sql = "update moneydog.for_help set state = 6 where uid1 = ? and fid = ? and state != 1; ";state = "select state from for_help where fid = ?;";paymoney = "select pay from expressage where pid = ?;";break;//求助
            case 3:sql = "update moneydog.second_hand set state = 6 where uid1 = ? and sid = ? and state != 1; ";state = "select state from second_hand where sid = ?;";paymoney = "select pay from expressage where pid = ?;";break;//二手
            default:break;
        }


        String errmsg;
        int errcode;
        try{
            errcode = jdbcTemplate.update(sql,openid,id);
            if(errcode == 1){
                errmsg = "Delete succeessfully";
                if(type != 3){
                    int state_num = jdbcTemplate.queryForObject(state,new Object[]{id},int.class);
                    int pay = jdbcTemplate.queryForObject(paymoney,new Object[]{id},int.class);
                    if(state_num != 2){
                        JSONObject temp2 =  CreateIssueBalanceDetail(pay,"删除订单，返回费用",openid);
                        jt.put("statecode",temp2.getInteger("statecode"));
                        jt.put("msg",temp2.getString("msg"));
                    }
                }
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
