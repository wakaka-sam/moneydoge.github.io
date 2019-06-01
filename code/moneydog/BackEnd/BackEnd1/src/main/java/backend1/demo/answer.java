
package backend1.demo;

public class answer {
    private int type;
    private int a;
    private int b;
    private int c;
    private int d;
    private String fill;
    answer(){

    }
    answer(int type,int A,int B,int C,int D,String fill){
        this.type = type;
        this.a = A;
        this.b = B;
        this.c = C;
        this.d = D;
        this.fill = fill;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }
}
