import management.MenuManagement;
import model.Item;
import model.Menu;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        MenuManagement mm = new MenuManagement();
        var menu = Menu.builder()
                .id(UUID.randomUUID().toString())
                .type("food")
                .build();
        var menu1 = Menu.builder()
                .id(UUID.randomUUID().toString())
                .type("drink")
                .build();
        mm.createOrUpdateMenu(menu);
        mm.createOrUpdateMenu(menu1);
        var item = Item.builder()
                .id(UUID.randomUUID().toString())
                .name("Bun bo hue")
                .description("Bun bo hue ngon lam")
                .note("Do something")
                .price(25000)
                .quality(2)
                .type("breakfast")
                .build();
        mm.createOrUpdateItem(item, menu.getId());
    }
}