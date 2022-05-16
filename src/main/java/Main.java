import management.MenuManagement;
import model.Menu;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        MenuManagement mm = new MenuManagement();
        var menu = Menu.builder()
                .id(UUID.randomUUID().toString())
                .type("food")
                .build();
        mm.getAllMenus();
    }
}