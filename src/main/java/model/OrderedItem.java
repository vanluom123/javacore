package model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderedItem {
    @CsvBindByPosition(position = 0)
    private String billId;
    @CsvBindByPosition(position = 1)
    private String itemId;
    @CsvBindByPosition(position = 2)
    private Bill bill;
    @CsvBindByPosition(position = 3)
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
