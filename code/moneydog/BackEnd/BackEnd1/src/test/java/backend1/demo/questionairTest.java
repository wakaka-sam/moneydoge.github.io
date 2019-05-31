package backend1.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class questionairTest {
    //测试
    public static void main(String[] args) {

        try{
            String con = "\"content\":[{\"type\":0,\"A\":0,\"B\":0,\"C\":0,\"D\":0,\"fill\":\"\"},{\"type\":0,\"A\":0,\"B\":0,\"C\":0,\"D\":0,\"fill\":\"\"},{\"type\":0,\"A\":0,\"B\":0,\"C\":0,\"D\":0,\"fill\":\"\"}]";
            JSONObject T =  new JSONObject();
            T.put("content",con);
            JSONObject content = JSON.parseObject(JSONObject.toJSONString(T));

            JSONArray arry = content.getJSONArray("content");

            List<answer> temp = new ArrayList<answer>();
            for (int i = 0;i < arry.size();i++){
                int type = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getInteger("type");
                int A = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getInteger("A");
                int B = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getInteger("B");
                int C = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getInteger("C");
                int D = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getInteger("D");
                String fill = JSONObject.parseObject(JSONObject.toJSONString(arry.get(i))).getString("fill");
                temp.add(new answer(type,A,B,C,D,fill));
            }
            System.out.println(temp.get(0).getC());

        }catch (Exception e){
            System.out.println(e);
        }

//        //json数据格式为对象形式:{}
//        String jsonData1="{\"modifyTempStoreArr\":"
//                + "{\"floorId\":\"2\",\"goodsCatId\":\"5768\",\"channelStoreyId\":\"256\",\"storeyImg\":\"http://qjnanjing.oss-cn-shanghai.aliyuncs.com/qj_nanjing/1504176460708.jpg\",\"storeyImgHref\":\"www.baidu.com\",\"storeyName\":\"精品服饰\"}"
//                + "}";
//        //json数据格式为对象数组形式: [{},{}]
//        String jsonData2="{\"modifyChannelAdverArr\":["
//                + "{\"floorId\":\"256\",\"adverHref\":\"\",\"temp3\":\"0\",\"adverType\":\"151\",\"adverFlag\":\"2\",\"adverSort\":\"1\",\"adverPath\":\"http://qjnanjing.oss-cn-shanghai.aliyuncs.com/qj_nanjing/1492671908992.jpg\",\"adverName\":\"1\",\"atId\":\"161\",\"channelAdverId\":\"2517\",\"temp5\":\"0\"},"
//                + "{\"floorId\":\"256\",\"adverHref\":\"\",\"temp3\":\"0\",\"adverType\":\"151\",\"adverFlag\":\"2\",\"adverSort\":\"2\",\"adverPath\":\"http://qjnanjing.oss-cn-shanghai.aliyuncs.com/qj_nanjing/1492671908992.jpg\",\"adverName\":\"2\",\"atId\":\"161\",\"channelAdverId\":\"2518\",\"temp5\":\"0\"}"
//                + "]}";
//
//        //首先将json字符串转为为json对象
//        JSONObject json1 = JSON.parseObject(jsonData1);
//        JSONObject json2 = JSON.parseObject(jsonData2);
//        System.out.println("json1:"+json1.toJSONString());
//        System.out.println("json2:"+json2.toJSONString());
//
//        //然后通过key名称获得值
//        JSONObject object=json1.getJSONObject("modifyTempStoreArr");
//        JSONArray array2=json2.getJSONArray("modifyChannelAdverArr");
//        System.out.println("object:"+object.toJSONString());
//        System.out.println("array2:"+array2.toJSONString());
//
//        //解析json数据
//        //方法一：
//        String storeyName=object.getString("storeyName");
//        Long goodsCatId=object.getLong("goodsCatId");
//        String storeyImg=object.getString("storeyImg");
//        String storeyImgHref=object.getString("storeyImgHref");
//        Integer floorIds=object.getInteger("floorId");
//        Long channelStoreyId=object.getLong("channelStoreyId");
//        System.out.println("storeyName:"+storeyName);
//        System.out.println("goodsCatId:"+goodsCatId);
//        System.out.println("storeyImg:"+storeyImg);
//        System.out.println("storeyImgHref:"+storeyImgHref);
//        System.out.println("floorId:"+floorIds);
//        System.out.println("channelStoreyId:"+channelStoreyId);
//
//        for(int i=0;i<array2.size();i++){
//            String adverName=JSONObject.parseObject(JSONObject.toJSONString(array2.get(i))).getString("adverName");
//            String adverPath=JSONObject.parseObject(JSONObject.toJSONString(array2.get(i))).getString("adverPath");
//            String adverHref=JSONObject.parseObject(JSONObject.toJSONString(array2.get(i))).getString("adverHref");
//            Integer adverSort=JSONObject.parseObject(JSONObject.toJSONString(array2.get(i))).getInteger("adverSort");
//            Integer adverFlag=JSONObject.parseObject(JSONObject.toJSONString(array2.get(i))).getInteger("adverFlag");
//            Long floorId=JSONObject.parseObject(JSONObject.toJSONString(array2.get(i))).getLong("floorId");
//            Long atId=JSONObject.parseObject(JSONObject.toJSONString(array2.get(i))).getLong("atId");
//            Long adverType=JSONObject.parseObject(JSONObject.toJSONString(array2.get(i))).getLong("adverType");
//            String temp3=JSONObject.parseObject(JSONObject.toJSONString(array2.get(i))).getString("temp3");
//            String temp5=JSONObject.parseObject(JSONObject.toJSONString(array2.get(i))).getString("temp5");
//            Long channelAdverId=JSONObject.parseObject(JSONObject.toJSONString(array2.get(i))).getLong("channelAdverId");
//            System.out.println("adverName:"+adverName);
//            System.out.println("adverPath:"+adverPath);
//            System.out.println("adverHref:"+adverHref);
//            System.out.println("adverSort:"+adverSort);
//            System.out.println("adverFlag:"+adverFlag);
//            System.out.println("floorId:"+floorId);
//            System.out.println("atId:"+atId);
//            System.out.println("adverType:"+adverType);
//            System.out.println("temp3:"+temp3);
//            System.out.println("temp5:"+temp5);
//            System.out.println("channelAdverId:"+channelAdverId);



    }
}