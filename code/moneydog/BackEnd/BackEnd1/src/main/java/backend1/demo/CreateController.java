package backend1.demo;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequestMapping("/Create")
@RestController
public class CreateController {
    @Autowired
    private  CreateService createService;

    @PostMapping("/Expressage")
    public String CreateExpressage(@RequestParam("express_loc") String express_loc,@RequestParam("arrive_time") Date arrive_time,@RequestParam("loc") String loc,@RequestParam("num") int num,@RequestParam("pay") int pay,@RequestParam("remark") String remark,@RequestParam("phone") String phone,@RequestParam("wechat") String wechat){
        return  createService.CreateExpressage(express_loc,arrive_time,loc,num,pay,remark,phone,wechat);
    }

    @ResponseBody
    @RequestMapping(value = "/For_help",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String CreateFor_help(@RequestParam("title") String title,@RequestParam ("phone") String phone,@RequestParam("wechat") String wechat,@RequestParam("working_time") Date working_time,@RequestParam("pay") int pay){
         createService.CreateFor_help(title,phone,wechat,working_time,pay);
        return "1";
    }
    @PostMapping("/Errand")
    public  String CreateErrand(@RequestParam("title") String title,@RequestParam("phone") String phone,@RequestParam("wechat") String wechat,@RequestParam("working_time") Date working_time,@RequestParam("pay") int pay){
        return createService.CreateErrand(title,phone,wechat,working_time,pay);
    }
    @PostMapping("/Second_hand")
    public  String CreateSecond_hand(@RequestParam("object_name") String object_name,@RequestParam("phone") String phone,@RequestParam("wechat") String wechat,@RequestParam("working_time") Date working_time,@RequestParam("pay") int pay,@RequestParam("photo_url") String photo_url){
       return createService.CreateSecond_hand(object_name,phone,wechat,working_time,pay,photo_url);
    }
}
