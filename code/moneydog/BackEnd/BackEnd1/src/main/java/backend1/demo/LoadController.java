package backend1.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/Load")
@RestController
public class LoadController {

    private LoadSerivce loadSerivce;

    @RequestMapping(method = RequestMethod.GET,value = "/downLoadExpressage")
    public List<expressage> downLoadExpressage(@RequestParam("pid") int pid ){
        return loadSerivce.downLoadExpressage(pid);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/downLoadErrands")
    public List<errand> downLoadErrands(@RequestParam("rid")int rid){
        return  loadSerivce.downLoadErrands(rid);
    }
    @RequestMapping(method = RequestMethod.GET,value = "/downLoadFor_help")
    public List<for_help> downLoadFor_help(@RequestParam("fid")int fid){
        return loadSerivce.downLoadFor_help(fid);
    }
    @RequestMapping(method = RequestMethod.GET,value = "/downLoadSecond_hand")
    public List<second_hand> downLoadSecond_hand(@RequestParam("sid") int sid){
        return loadSerivce.downLoadSecond_hand(sid);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/OnLoadExpressage")
    public List<expressage> OnLoadExpressage(){
        return loadSerivce.OnLoadExpressage();
    }
    @RequestMapping(method = RequestMethod.GET,value = "/OnLoadErrands")
    public List<errand> OnLoadErrands(){
        return loadSerivce.OnLoadErrands();
    }
    @RequestMapping(method = RequestMethod.GET,value = "/OnLoadFor_help")
    public List<for_help> OnLoadFor_help(){
        return loadSerivce.OnLoadFor_help();
    }
    @RequestMapping(method = RequestMethod.GET,value = "/OnLoadSecond_hand")
    public List<second_hand> OnLoadSecond_hand(){
        return loadSerivce.OnLoadSecond_hand();
    }
}
