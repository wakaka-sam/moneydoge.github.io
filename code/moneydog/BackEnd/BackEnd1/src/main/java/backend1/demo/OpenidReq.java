package backend1.demo;

public class OpenidReq {


    public String openid;
    public String session_key;
    public String unionid;
    public int errcode;
    public String errmsg;
    OpenidReq(String openid,String session_key,String unionid,int errcode,String errmsg){
        this.openid = openid;
        this.session_key = session_key;
        this.unionid = unionid;
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    // res{
    //      openid,
    //      session_key,
    //      unionid,
    //      errcode,
    //      errmsg
    //  }
}
