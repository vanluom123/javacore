package model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
public class Menu {
    @CsvBindByPosition(position = 0)
    private String id;
    @CsvBindByPosition(position = 1)
    private String type;
    private Set<Item> menuItems;

    @Override
    public String toString() {
        return "Model.Menu{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", menuItems=" + menuItems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id) || Objects.equals(type, menu.type);
    }
}
