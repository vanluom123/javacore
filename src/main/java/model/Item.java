package model;

import lombok.Builder;
import lombok.Data;

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
}
