package backend1.demo;

public class req {
    private String type;
    private String uid;
    private int price;

    req(String type,String uid,int price){
        this.type = type;
        this.uid = uid;
        this.price = price;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
