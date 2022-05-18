package model;

import management.MenuManagement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class MenuTest {

    private static MenuManagement mm;

    @BeforeAll
    static void setUp() {
        mm = new MenuManagement();
    }

    @Test
    public void createOrUpdateMenu_Test() {
        Menu menu = new Menu();
        menu.setId(UUID.randomUUID().toString());
        menu.setType("food");
        Assertions.assertTrue(mm.createOrUpdateMenu(menu));

        Menu menu1 = new Menu();
        menu1.setId(UUID.randomUUID().toString());
        menu1.setType("drink");
        Assertions.assertTrue(mm.createOrUpdateMenu(menu1));
    }

    @Test
    public void getMenuById_Test() {
        var lstMenu = mm.getAllMenus();
        Assertions.assertDoesNotThrow(() -> new Exception());
        boolean check = lstMenu.stream()
                .allMatch(menu -> mm.getMenuById(menu.getId()) != null);
        Assertions.assertTrue(check);
    }

    @Test
    public void createOrUpdateItem_Test() {
        Item item = new Item();
        item.setId(UUID.randomUUID().toString());
        item.setMenuId("976a9aa4-023b-4603-8482-564c52e1f108");
        item.setName("Pizza");
        item.setDescription("Pizza description");
        item.setNote("Pizza note here");
        item.setPrice(20000);
        item.setQuality(1);
        item.setType("launch");

        Assertions.assertTrue(mm.createOrUpdateItem(item, "976a9aa4-023b-4603-8482-564c52e1f108"));
    }

    @Test
    public void deleteMenuById_Test() {
        var lstMenu = mm.getAllMenus();
        var iter = lstMenu.iterator();
        while(iter.hasNext()) {
            var value = iter.next();
            mm.deleteById(value.getId());
        }
        var lstMenu2 = mm.getAllMenus();
        Assertions.assertNotEquals(lstMenu, lstMenu2);
    }
}
