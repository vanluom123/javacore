package model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class Item {
    @CsvBindByPosition(position = 0)
    private String id;
    @CsvBindByPosition(position = 1)
    private String menuId;
    @CsvBindByPosition(position = 2)
    private String name;
    @CsvBindByPosition(position = 3)
    private String description;
    @CsvBindByPosition(position = 4)
    private int quality;
    @CsvBindByPosition(position = 5)
    private int price;
    @CsvBindByPosition(position = 6)
    private String note;
    @CsvBindByPosition(position = 7)
    private String type;
    private Menu menu;
    private Set<OrderedItem> orderedItems;

    @Override
    public String toString() {
        return "Model.Item{" +
                "id='" + id + '\'' +
                ", menuId='" + menuId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quality=" + quality +
                ", price=" + price +
                ", note='" + note + '\'' +
                ", type='" + type + '\'' +
                ", menu=" + menu +
                ", orderedItems=" + orderedItems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
