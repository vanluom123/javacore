package model;

import constants.AppConstants;
import management.BillManagement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.UUID;

public class BillTest {
    private static BillManagement bm;

    @BeforeAll
    static void setUp() {
        bm = new BillManagement();
    }

    @Test
    public void create_Or_Update_OrderItem_Test() {
        OrderedItem item = new OrderedItem();
        item.setItemId("4f567187-b15b-450b-a566-56d32bf56490");
        item.setBillId("6270150e-cd40-4e57-94bf-0460e4a25644");
        Assertions.assertTrue(bm.createOrUpdateOrderItem(item));
    }

    @Test
    public void create_Or_Update_Bill_Test() {
        Bill bill = new Bill();
        bill.setId(UUID.randomUUID().toString());
        bill.setOrderedTime(Objects.requireNonNull(AppConstants.getCurrentUtcTime()).toString());
        bill.setTotalPrice(0);
        Assertions.assertTrue(bm.createOrUpdateBill(bill));
    }

    @Test
    public void getBillById_Test() {
        Bill bill = bm.getBillById("dc27cc3c-e3d7-439e-bcc8-71c8fefc5bf4");
        Assertions.assertDoesNotThrow(() -> new Exception());
        Assertions.assertTrue(() -> bill != null);
    }

    @Test
    public void deleteBillById_Test() {
        bm.deleteBillById("dc27cc3c-e3d7-439e-bcc8-71c8fefc5bf4");
        Bill bill = bm.getBillById("dc27cc3c-e3d7-439e-bcc8-71c8fefc5bf4");
        Assertions.assertTrue(() -> bill == null);
    }
}
