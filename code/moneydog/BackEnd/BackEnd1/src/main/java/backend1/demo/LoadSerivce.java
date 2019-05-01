package backend1.demo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoadSerivce {
    @Autowired
    LoadMapper loadMapper;

    public List<expressage> downLoadExpressage(int pid ){
        return loadMapper.downLoadExpressage(pid);
    }
    public List<errand> downLoadErrands(int rid){
        return loadMapper.downLoadErrands(rid);
    }
    public List<for_help> downLoadFor_help(int fid){
        return downLoadFor_help(fid);
    }
    public List<second_hand> downLoadSecond_hand(int sid){
        return downLoadSecond_hand(sid);
    }

    public List<expressage> OnLoadExpressage(){
        return OnLoadExpressage();
    }
    public List<errand> OnLoadErrands(){
        return OnLoadErrands();
    }
    public List<for_help> OnLoadFor_help(){
        return OnLoadFor_help();
    }
    public List<second_hand> OnLoadSecond_hand(){
        return OnLoadSecond_hand();
    }
}
