package backend1.demo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedMultiValueMap;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequestMapping("/Create")
@Api(description = "问卷的API和其他订单的创建")
@RestController
public class CreateController {
    @Autowired
    private CreateService createService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;



    @RequestMapping(value = "/viewAll",method = RequestMethod.GET)
    @ApiOperation(value = "发布者获取问卷填写统计：人数，内容统计等",notes = "errcode 0 失败，1 成功，成功可通过viewAllList 获取所有信息")
    public JSONObject viewAll(@RequestHeader("sessionId")String sessionId,@RequestParam("id")int id){
        String openid = getOpenidFromSession(sessionId);
        String sql = "select name,description,content,content_count,num,state from questionair where qid = ? and uid = ?;";
        JSONObject jsonObject = new JSONObject();

        try{
           List<question> temp =  jdbcTemplate.query(sql,new Object[]{id,openid},new BeanPropertyRowMapper(question.class));
           jsonObject.put("errcode",1);
           jsonObject.put("errmsg","successfully");
           jsonObject.put("viewAllList",temp.get(0));

        }catch (Exception e){
            jsonObject.put("errcode",0);
            jsonObject.put("errmsg","fail");
            System.out.println(e);
        }
        return jsonObject;
    }

    //创建问卷
    @RequestMapping(value = "/questionair" ,method = RequestMethod.POST)
    public JSONObject create(@RequestHeader("sessionId")String sessionId,@RequestParam("pay")int pay, @RequestParam("total_num")int total_num,@RequestParam("name") String name,@RequestParam("description") String description,@RequestParam("content")String content,@RequestParam("content_count")String content_count){


        String sql = "insert into questionair(uid,pay,name,description,content,content_count,total_num,num,state)values(?,?,?,?,?,?,?,0,0)";
        String openid = getOpenidFromSession(sessionId);
        int total_price = 0 - total_num*pay;
        JSONObject balance =  CreateIssueBalanceDetail(total_price,"创建问卷",openid,"");
        if(balance.getInteger("statecode") == -1){
            balance.put("errcode",2);
            balance.put("errmsg","余额错误");
            return balance;
        }
        JSONObject jsonObject = new JSONObject();
        int t = 0;
        try{
            t = jdbcTemplate.update(sql,openid,pay,name,description,content,content_count,total_num);
            jsonObject.put("errmsg","Create suc");
        }catch (Exception e){
            System.out.println(e);
            jsonObject.put("errmsg","Create failed");
        }
        jsonObject.put("errcode",t);
        return jsonObject;
    }

    private List<answer> getFromJson(String con){
        JSONObject content = JSON.parseObject(con);
        JSONArray arry = content.getJSONArray("content_count");

        List<answer> temp = new ArrayList<answer>();
        try {
            for (int i = 0;i < arry.size();i++){
                int type = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getInteger("type");
                int A = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getInteger("a");
                int B = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getInteger("b");
                int C = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getInteger("c");
                int D = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getInteger("d");
                String fill = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getString("fill");
                temp.add(new answer(type,A,B,C,D,fill));
            }
        }catch (Exception e){
            System.out.println(e);
            System.out.println(2);
        }

        return  temp;
    }


    //发送问卷的填写内容
    @RequestMapping(value = "/fill",method =  RequestMethod.POST)
    public JSONObject fill(@RequestHeader("sessionId")String sessionId ,@RequestParam("content_count") String con,@RequestParam("id")int id){

        String openid = getOpenidFromSession(sessionId);
        JSONObject jsonObject = new JSONObject();
        int t = 0;
        try {
            t = jdbcTemplate.queryForObject("select 1 from questionairNote where uid = ? and qid = ?",new Object[]{openid,id},int.class);
            jsonObject.put("errcode",2);
            jsonObject.put("errmsg","you have fill this questionair");
            return jsonObject;
        }catch (Exception e){
            System.out.println("进行填写");
//            System.out.println(e);
        }
        System.out.println(con);
        //获取前端填写的问卷内容
        List<answer> fillItem = getFromJson(con);
        String sql = "select content_count from questionair where qid = ?;";
        String ctt = "";
        int num = 0;
        try {
            String temp = jdbcTemplate.queryForObject(sql,new Object[]{id},String.class);
            num = jdbcTemplate.queryForObject("select num from questionair where qid = ?",new Object[]{id},int.class);
            System.out.println(temp);
            ctt = temp;

        }catch (Exception e){
            num = -1;
//            System.out.println("");
            System.out.println(e);
        }
        System.out.println( "222"+ctt);
        List<answer> content_count = getFromJson(ctt);
        System.out.println(content_count);
        for (int i = 0;i < fillItem.size();i++){
            int type = fillItem.get(i).getType();
            if(type == 2){
                String fill = fillItem.get(i).getFill();
                String fill_content_count = content_count.get(i).getFill();
                //设置内容
                content_count.get(i).setFill(fill_content_count+fill);
            }else {
//                upperCase are from database,x_temp are from from end;
                int A,B,C,D,A_temp,B_temp,C_temp,D_temp;
                A = content_count.get(i).getA();
                B = content_count.get(i).getB();
                C = content_count.get(i).getC();
                D = content_count.get(i).getD();

                A_temp = fillItem.get(i).getA();
                B_temp = fillItem.get(i).getB();
                C_temp = fillItem.get(i).getC();
                D_temp = fillItem.get(i).getD();

                //设置选项数量
                content_count.get(i).setA(A+A_temp);
                content_count.get(i).setB(B+B_temp);
                content_count.get(i).setC(C+C_temp);
                content_count.get(i).setD(D+D_temp);
            }
        }

        String content_after;
        JSONObject j = new JSONObject();
        j.put("content_count",content_count);
        content_after = JSONObject.toJSONString(j);
        try {
            t =  jdbcTemplate.update("update questionair set content_count = ? where qid = ?",content_after,id);
            if(num != -1){
                num += 1;
                jdbcTemplate.update("update questionair set num = ? where qid = ?",num,id);
                int total_num =  jdbcTemplate.queryForObject("select total_num from questionair where qid = ?;",new Object[]{id},int.class);
                if(num == total_num){
                    jdbcTemplate.update("update questionair set state = 2 where qid = ?;",id);
                }
            }
            jsonObject.put("errmsg","fill suc");
        }catch (Exception e){
            t = 0;
            System.out.println(e);
            jsonObject.put("errmsg","fill failed");
        }
        // t = 0,1,2,:创建失败，创建成功，重复填写问卷
        jsonObject.put("errcode",t);
        if(t == 1){
            try {
                int pay = jdbcTemplate.queryForObject("select pay from questionair where qid = ?;",new Object[]{id},int.class);
                JSONObject temp = CreateIssueBalanceDetail(pay,"填写问卷的报酬",openid,"");
                jdbcTemplate.update("insert into questionairNote(uid,qid) values(?,?)",openid,id);
                jsonObject.put("statecode",temp.getInteger("statecode"));
                jsonObject.put("msg",temp.getString("msg"));
            }catch (Exception e){
                System.out.println(e);
                System.out.println("填写问卷");
                jsonObject.put("statecode",4);
                jsonObject.put("msg","服务端出错");
            }
        }
        return jsonObject;
    }
    //删除问卷
    @RequestMapping(value = "/DeleteQuestionair",method = RequestMethod.GET)
    @ApiOperation(value = "删除问卷",notes = "errcode = 1,成功，0,失败，可查看errmsg内容，问卷的state变为0")
    public JSONObject DeleteQuestionair(@RequestHeader("sessionId")String sessionId,@RequestParam("id")int id){
        String openid = getOpenidFromSession(sessionId);
        String sql = "update questionair set state = 3 where uid = ? and qid = ?;";
        JSONObject jsonObject = new JSONObject();
        int state = 0;
        int t = 0;
        try {
            state = jdbcTemplate.queryForObject("select state from questionair where qid = ?;",new Object[]{id},int.class);
            t = jdbcTemplate.update(sql,openid,id);
            jsonObject.put("errmsg","Delete suc");
        }catch (Exception e){
            System.out.println(e);
            jsonObject.put("errmsg","Delete failed");
        }
        //        给用户返回金额
        try {
            int total_num = jdbcTemplate.queryForObject("select total_num from questionair where qid = ?;",new Object[]{id},int.class);
            int num = jdbcTemplate.queryForObject("select num from questionair where qid = ?;",new Object[]{id},int.class);
            int pay = jdbcTemplate.queryForObject("select pay from questionair where qid = ?;",new Object[]{id},int.class);
            int price = pay*(total_num - num);
            System.out.println(state);
            if(state != 2){
                JSONObject temp = CreateIssueBalanceDetail(price,"删除问卷获得剩余金额",openid,"");
                jsonObject.put("statecode",temp.getInteger("statecode"));
                jsonObject.put("msg",temp.getString("msg"));
            }

        }catch (Exception e){
            System.out.println(e);
            jsonObject.put("statecode",4);
            jsonObject.put("msg","服务器错误");
        }
        jsonObject.put("errcode",t);
        return  jsonObject;
    }

    //终止问卷
    @RequestMapping(value = "/EndQuestion",method = RequestMethod.GET)
    @ApiOperation(value = "用户终止订单，用户收集到足够多的订单后，终止问卷",notes = "")
    public JSONObject EndQuestion(@RequestHeader("sessionId")String sessionId,@RequestParam("id")int id){
        String openid = getOpenidFromSession(sessionId);
        String sql = "update questionair set state = 2 where uid = ? and qid = ?;";
        JSONObject jsonObject = new JSONObject();
        int t = 0;
        try {
            t = jdbcTemplate.update(sql,openid,id);
            jsonObject.put("errmsg","End suc");
        }catch (Exception e){
            System.out.println(e);
            jsonObject.put("errmsg","End failed");
        }
//        给用户返回金额
        try {
            int total_num = jdbcTemplate.queryForObject("select total_num from questionair where qid = ?;",new Object[]{id},int.class);
            int num = jdbcTemplate.queryForObject("select num from questionair where qid = ?;",new Object[]{id},int.class);
            int pay = jdbcTemplate.queryForObject("select pay from questionair where qid = ?;",new Object[]{id},int.class);
            int price = pay *(total_num - num);
            JSONObject temp = CreateIssueBalanceDetail(price,"终止问卷获得剩余金额",openid,"");
            jsonObject.put("statecode",temp.getInteger("statecode"));
            jsonObject.put("msg",temp.getString("msg"));
        }catch (Exception e){
            System.out.println(e);
            jsonObject.put("statecode",4);
            jsonObject.put("msg","服务器错误");
        }
        jsonObject.put("errcode",t);
        return  jsonObject;
    }
    //加载问卷
    @RequestMapping(value = "/OnLoadQuestionair",method = RequestMethod.GET)
    public List<LoadQuestion> OnLoadQuestionair(){
        try {
            int id = jdbcTemplate.queryForObject("SELECT COUNT(qid) AS NumberOfProducts FROM questionair;", int.class);
            System.out.println(id);
            return jdbcTemplate.query("select qid,name,description,pay from questionair where qid <= ? and state = 0 and num < total_num order by qid desc limit 15 ;",new Object[]{id},new BeanPropertyRowMapper(LoadQuestion.class));
        }catch (Exception e){
            System.out.println("加载问卷错误");
            System.out.println(e);
            return  null;
        }

    }
    //加载问卷
    @RequestMapping(value = "/LoadMyQuestionair",method = RequestMethod.GET)
    public List<LoadQuestion> LoadMyQuestionair(@RequestHeader("sessionId")String sessionId){
        String openid = getOpenidFromSession(sessionId);
        try {
//            int id = jdbcTemplate.queryForObject("SELECT COUNT(qid) AS NumberOfProducts FROM questionair;", int.class);
            return jdbcTemplate.query("select qid,name,description,pay,state,num from questionair where uid = ? and state != 3 order by qid desc ;",new Object[]{openid},new BeanPropertyRowMapper(LoadQuestion.class));
        }catch (Exception e){
            System.out.println("加载问卷错误");
            System.out.println(e);
            return  null;
        }
    }
    //上拉问卷
    @RequestMapping(value = "/downLoadQuestionair",method = RequestMethod.GET)
    public List<LoadQuestion> downLoadQuestionair(@RequestParam("id")int id){
        try {
            return jdbcTemplate.query("select qid,name,description from questionair where qid <= ? and state = 0 and num < total_num order by qid desc limit 15 ;", new Object[]{id}, new BeanPropertyRowMapper(LoadQuestion.class));

        }catch (Exception e){
            System.out.println("上啦问卷错误");
            System.out.println(e);
            return  null;
        }
    }


    @RequestMapping(value = "/getQuestionairContent",method = RequestMethod.GET)
    public List<question> getQuestionairContent(@RequestParam("id")int id){
        return jdbcTemplate.query("select qid,name ,description,content from questionair where qid = ?",new Object[]{id},new BeanPropertyRowMapper(question.class));
    }


    private JSONObject CreateIssueBalanceDetail(int price,String detail,String uid,String path){

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

    private JSONObject GetOpenId(String code) {
        //得到完整的
        //{
        // errcode,
        // errmsg,
        // openid
        // session_key
        // }
        RestTemplate restTemplate = new RestTemplate();
        String appid = "wxd128011688abed28&";
        String secret = "44663739b9268649d3740990f49a2677&";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid
                + "&secret=" + secret
                + "&js_code=" + code
                + "&grant_ty pe=authorization_code";
        JSONObject jsonObject = new JSONObject();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String strbody = "";
        JSONObject res = new JSONObject();
        try {
            strbody = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
            res = JSONObject.parseObject(strbody, JSONObject.class);
        }catch (Exception e){
            System.out.println(e);
        }
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
            System.out.println(e);
        }
        String openid = "";
        String session_key = "";
        try {
            openid = res.getString("openid");
            session_key = res.getString("session_key");
        } catch (Exception e) {
            System.out.println(e);
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
        JSONObject time = new JSONObject();
        if (createService.Login(openid) == 1) {
            //生成sessionId
            String SessionId = UUID.randomUUID().toString();
            //存入redis
            stringRedisTemplate.opsForValue().set(SessionId, RedisSession.toString(), 60, TimeUnit.MINUTES);

            //返回sessionId
            time.put("errcode",1);
            time.put("errmsg","Login in successfully");
            time.put("SessionId", SessionId);
            time.put("expireTime", 60);
        }else{
            time.put("errcode",0);
            time.put("errmsg","Please First Register");
        }
        return time;
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

    @RequestMapping(method = RequestMethod.POST, value = "/Login")
    public JSONObject Login(@RequestParam("code") String code) {

        JSONObject res = GetOpenId(code);
        if (res.getInteger("errcode") != 0)
            return res;

        JSONObject session = setSessionId(res);
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
        JSONObject jsonObject = CreateIssueBalanceDetail(-pay,"创建快递",openid,"");
        if(jsonObject.getInteger("statecode")!= 1)
        {
            jsonObject.put("errcode",2);
            jsonObject.put("errmsg","余额错误");
            return jsonObject;
        }
//        String thid = jsonObject.getString("thid");
        return createService.CreateExpressage(openid,express_loc, arrive_time, loc, num, pay, remark, phone, wechat);
    }

    @ResponseBody
    @RequestMapping(value = "/For_help", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject CreateFor_help(@RequestHeader("sessionId")String sessionId,@RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("phone") String phone, @RequestParam("wechat") String wechat, @RequestParam("ending_time") Date ending_time, @RequestParam("pay") int pay) {
        String openid = getOpenidFromSession(sessionId);

        JSONObject jsonObject = CreateIssueBalanceDetail(-pay,"创建求助",openid,"");
        if(jsonObject.getInteger("statecode")!= 1)
        {
            jsonObject.put("errcode",2);
            jsonObject.put("errmsg","余额错误");
            return jsonObject;
        }
        return createService.CreateFor_help(openid,title, content, phone, wechat, ending_time, pay);
    }

    @PostMapping("/Errand")
    public JSONObject CreateErrand(@RequestHeader("sessionId")String sessionId,@RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("phone") String phone, @RequestParam("wechat") String wechat, @RequestParam("ending_time") Date ending_time, @RequestParam("pay") int pay) {
        String openid = getOpenidFromSession(sessionId);

        JSONObject jsonObject = CreateIssueBalanceDetail(-pay,"创建跑腿",openid,"");
        if(jsonObject.getInteger("statecode")!= 1)
        {
            jsonObject.put("errcode",2);
            jsonObject.put("errmsg","余额错误");
            return jsonObject;
        }
//        String thid = jsonObject.getString("thid");
        return createService.CreateErrand(openid,title, content, phone, wechat, ending_time, pay);
    }
//        @PostMapping("/Errand")
//        public JSONObject CreateErrand(@RequestBody String title, @RequestBody String content, @RequestBody String phone, @RequestBody String wechat, @RequestBody Date ending_time, @RequestBody int pay) {
//            return createService.CreateErrand(title, content, phone, wechat, ending_time, pay);
//        }

    @PostMapping("/Second_hand")
    public JSONObject CreateSecond_hand(@RequestHeader("sessionId")String sessionId,@RequestParam("object_name") String object_name, @RequestParam("content") String content, @RequestParam("phone") String phone, @RequestParam("wechat") String wechat, @RequestParam("ending_time") Date ending_time, @RequestParam("pay") int pay, @RequestParam("photo_url") String photo_url) {
        String openid = getOpenidFromSession(sessionId);
//        JSONObject jsonObject = CreateIssueBalanceDetail(pay,"二手",openid,"createHistory");
//        if(jsonObject.getInteger("statecode")!= 1)
//        {
//            jsonObject.put("errcode",2);
//            jsonObject.put("errmsg","余额错误");
//            return jsonObject;
//        }
        return createService.CreateSecond_hand(openid,object_name, content, phone, wechat, ending_time, pay, photo_url);
    }
}
