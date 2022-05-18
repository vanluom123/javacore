package validate;

import model.Menu;
import model.OrderedItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ValidationTest {

    private static Menu menu;

    @BeforeAll
    static void setUp() {
        menu = new Menu();
        menu.setId(UUID.randomUUID().toString());
        menu.setType("food");
    }

    @Test
    public void Invalid_Menu_Id_Test() {
        menu.setId(null);
        Assertions.assertTrue(Validation.getInstance().isInvalidId(menu));
    }

    @Test
    public void Invalid_OrderItem_Item_Id_Test() {
        OrderedItem item = new OrderedItem();
        item.setItemId(null);
        item.setBillId(UUID.randomUUID().toString());
        Assertions.assertTrue(Validation.getInstance().isInvalidId(item));
    }

    @Test
    public void Invalid_OrderItem_Bill_Id_Test() {
        OrderedItem item = new OrderedItem();
        item.setItemId(UUID.randomUUID().toString());
        item.setBillId(null);
        Assertions.assertTrue(Validation.getInstance().isInvalidId(item));
    }

    @Test
    public void Invalid_OrderItem_Bill_Item_Id_Test() {
        OrderedItem item = new OrderedItem();
        item.setItemId(null);
        item.setBillId(null);
        Assertions.assertTrue(Validation.getInstance().isInvalidId(item));
    }

    @Test
    public void Valid_OrderItem_Bill_Item_Id_Test() {
        OrderedItem item = new OrderedItem();
        item.setItemId(UUID.randomUUID().toString());
        item.setBillId(UUID.randomUUID().toString());
        Assertions.assertFalse(Validation.getInstance().isInvalidId(item));
    }
}
