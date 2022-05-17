package model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
public class Bill {
    @CsvBindByPosition(position = 0)
    private String id;
    @CsvBindByPosition(position = 1)
    private Date orderedTime;
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
