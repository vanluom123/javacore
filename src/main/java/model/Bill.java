package model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Bill {
    @CsvBindByPosition(position = 0)
    private String id;
    @CsvBindByPosition(position = 1)
    private String orderedTime;
    @CsvBindByPosition(position = 2)
    private int totalPrice;
    private Set<OrderedItem> orderedItems;

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", orderedTime=" + orderedTime +
                ", totalPrice=" + totalPrice +
                ", orderedItems=" + orderedItems +
                '}';
    }
}
