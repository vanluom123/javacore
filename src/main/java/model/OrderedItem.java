package model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderedItem {
    private String billId;
    private String itemId;
    private Bill bill;
    private Item item;

    @Override
    public String toString() {
        return "OrderedItem{" +
                "billId='" + billId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", bill=" + bill +
                ", item=" + item +
                '}';
    }
}
