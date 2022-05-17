package validate;

import model.Menu;
import model.OrderedItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.UUID;

public class ValidationTest {

    private static Menu menu;

    @BeforeAll
    static void setUp() {
        menu = Menu.builder()
                .id(UUID.randomUUID().toString())
                .type("food")
                .build();
    }

    @Test
    public void Invalid_Menu_Id_Test() {
        menu.setId(null);
        Assertions.assertTrue(Validation.getInstance().isInvalidId(menu));
    }

    @Test
    public void Invalid_OrderItem_Item_Id_Test() {
        OrderedItem item = OrderedItem.builder()
                .itemId(null)
                .billId(UUID.randomUUID().toString())
                .build();
        Assertions.assertTrue(Validation.getInstance().isInvalidId(item));
    }

    @Test
    public void Invalid_OrderItem_Bill_Id_Test() {
        OrderedItem item = OrderedItem.builder()
                .itemId(UUID.randomUUID().toString())
                .billId(null)
                .build();
        Assertions.assertTrue(Validation.getInstance().isInvalidId(item));
    }

    @Test
    public void Invalid_OrderItem_Bill_Item_Id_Test() {
        OrderedItem item = OrderedItem.builder()
                .itemId(null)
                .billId(null)
                .build();
        Assertions.assertTrue(Validation.getInstance().isInvalidId(item));
    }

    @Test
    public void Valid_OrderItem_Bill_Item_Id_Test() {
        OrderedItem item = OrderedItem.builder()
                .itemId(UUID.randomUUID().toString())
                .billId(UUID.randomUUID().toString())
                .build();
        Assertions.assertFalse(Validation.getInstance().isInvalidId(item));
    }
}
