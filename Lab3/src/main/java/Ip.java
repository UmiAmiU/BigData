import java.io.Serializable;

public class Ip implements Serializable {
    public int Count;
    public int Sum;
    public Ip(int count,int sum){
        Count=count;
        Sum=sum;
    }
    @Override
    public String toString(){
        return (Sum*1.0/Count)+","+Sum;
    }
}