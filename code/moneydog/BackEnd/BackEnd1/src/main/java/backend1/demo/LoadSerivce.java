package backend1.demo;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoadSerivce {
    @Autowired
    LoadMapper loadMapper;

    public List<expressage> downLoadExpressage(int pid) {
        return loadMapper.downLoadExpressage(pid);
    }

    public List<errand> downLoadErrands(int rid) {
        return loadMapper.downLoadErrands(rid);
    }

    public List<for_help> downLoadFor_help(int fid) {
        return loadMapper.downLoadFor_help(fid);
    }

    public List<second_hand> downLoadSecond_hand(int sid) {
        return loadMapper.downLoadSecond_hand(sid);
    }

    public List<expressage> OnLoadExpressage() {
        return loadMapper.OnLoadExpressage();
    }

    public List<errand> OnLoadErrands() {
        return loadMapper.OnLoadErrands();
    }

    public List<for_help> OnLoadFor_help() {
        return loadMapper.OnLoadFor_help();
    }

    public List<second_hand> OnLoadSecond_hand() {
        return loadMapper.OnLoadSecond_hand();
    }

    public JSONObject LoadMyCreation(String openid) {
        return loadMapper.LoadMyCreation(openid);
    }

    public JSONObject LoadMyReceiving(String openid) {
        return loadMapper.LoadMyReceiving(openid);
    }
}
