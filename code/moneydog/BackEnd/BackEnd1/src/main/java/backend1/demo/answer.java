
package backend1.demo;

public class answer {
    private int type;
    private int A;
    private int B;
    private int C;
    private int D;
    private String fill;
    answer(){

    }
    answer(int type,int A,int B,int C,int D,String fill){
        this.type = type;
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.fill = fill;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getA() {
        return A;
    }

    public void setA(int a) {
        A = a;
    }

    public int getB() {
        return B;
    }

    public void setB(int b) {
        B = b;
    }

    public int getC() {
        return C;
    }

    public void setC(int c) {
        C = c;
    }

    public int getD() {
        return D;
    }

    public void setD(int d) {
        D = d;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

}
