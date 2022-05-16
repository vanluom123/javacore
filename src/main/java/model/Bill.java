package model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class Bill {
    private String id;
    private Date orderedTime;
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
