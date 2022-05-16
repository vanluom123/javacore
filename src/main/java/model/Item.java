package model;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Data
@Builder
public class Item {
    private String id;
    private String menuId;
    private String name;
    private String description;
    private int quality;
    private int price;
    private String note;
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
