package backend1.demo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


@RequestMapping("/Questionair")
@RestController
public class questionair {
    @RequestMapping("/questionair")
    public JSONObject create(@RequestParam("sessionId")String sessionId, @RequestParam("name") String name,@RequestParam("description") String description,@RequestParam("content")String content,@RequestParam("content_count")String content_count){

        String sql = "insert into questionair(uid,pay,name,description,content,content_count,num,state)values(?,?,?,?,?,?,0,0)";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errcode","1");
        jsonObject.put("errmsg","");

        return jsonObject;
    }
    public  String getValueByReflet(Object model, String paraName) throws Exception {
        // 返回值
        String value = "";

        // 获取属性值
        Field[] fields = model.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            System.out.println(field.get(model));
        }
//        if (field.getName().equals(paraName)) {
//            value = (String) field.get(model);
//            break;
//        }
//        Long type = (Long)fields[0].get(model);
//        String title = (String)fields[1].get(model);
        System.out.println(fields[0].get(model));
        System.out.println(fields[1].get(model));

        return value;
    }

    private List<answer> getFromJson(String con){
        JSONObject content = JSON.parseObject(con);
        JSONArray arry = content.getJSONArray("content");

        List<answer> temp = new ArrayList<answer>();
        try {
            for (int i = 0;i < arry.size();i++){
                int type = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getInteger("type");
                int A = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getInteger("A");
                int B = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getInteger("B");
                int C = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getInteger("C");
                int D = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getInteger("D");
                String fill = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getString("fill");
                temp.add(new answer(type,A,B,C,D,fill));
            }
        }catch (Exception e){
            System.out.println(e);
        }

        return  temp;
    }


    @RequestMapping(value = "/fill",method =  RequestMethod.POST)
    @ResponseBody
    public JSONObject fill(@RequestBody String con){

        List<answer> fillItem = getFromJson(con);




        return null;
    }



}
 