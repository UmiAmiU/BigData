import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class Item implements Writable {

    public int Count;
    public int Sum;

    public int getCount() {
        return Count;
    }

    public int getSum() {
        return Sum;
    }

    public void setCount(int count) {
        Count = count;
    }

    public void setSum(int sum) {
        Sum = sum;
    }

    public  Item(){
    }

    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(Count);
        dataOutput.writeInt(Sum);
    }

    public void readFields(DataInput dataInput) throws IOException {
        Count= dataInput.readInt();
        Sum= dataInput.readInt();
    }
    @Override
    public String toString(){
        return ";"+(Sum*1.0/Count)+";"+Sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Count == item.Count &&
                Sum == item.Sum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Count, Sum);
    }
}
