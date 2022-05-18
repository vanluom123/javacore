package management;

import constants.AppConstants;
import lombok.extern.log4j.Log4j2;
import model.Bill;
import model.Item;
import model.OrderedItem;
import utils.OpenCsvReader;
import utils.OpenCsvWriter;
import validate.Validation;

import java.util.*;

@Log4j2
public class BillManagement {
    private final List<Bill> bills;

    public BillManagement() {
        bills = new ArrayList<>();
    }

    public void showBill() {
        try {
            OpenCsvReader.getInstance().parseToObject(Bill.class, AppConstants.BILL_CSV_PATH)
                    .forEach(System.out::println);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
        }
    }

    public boolean createOrUpdateOrderItem(OrderedItem orderedItem) {
        if (Validation.getInstance().isInvalidId(orderedItem))
            return false;

        var bill = getBillById(orderedItem.getBillId());
        if (bill == null)
            return false;

        List<Item> items;
        try {
            items = OpenCsvReader.getInstance().parseToObject(Item.class, AppConstants.ITEM_CSV_PATH);
            var existItem = items.stream().anyMatch(item -> item.getId().equals(orderedItem.getItemId()));
            if (!existItem)
                return false;
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }

        var orderItems = bill.getOrderedItems();
        if (orderItems == null || orderItems.isEmpty()) {
            if (orderItems == null)
                orderItems = new HashSet<>();
            orderItems.add(orderedItem);
        }

        OpenCsvWriter.getInstance().importToCsvFiles(orderItems, AppConstants.ORDER_ITEM_CSV_PATH);

        return true;
    }

    public boolean createOrUpdateBill(Bill bill) {
        if (Validation.getInstance().isInvalidId(bill))
            return false;

        if (bills.isEmpty()) {
            bills.add(bill);
        } else {
            if (bills.contains(bill)) {
                // update bill
                for (var o : bills) {
                    if (bill.getId().equals(o.getId())) {
                        bill.setOrderedTime(o.getOrderedTime());
                        bill.setTotalPrice(o.getTotalPrice());
                        bill.setOrderedItems(o.getOrderedItems());
                    }
                }
            } else {
                // create bill
                var insertingBill = new Bill();
                insertingBill.setId(UUID.randomUUID().toString());
                insertingBill.setOrderedTime(Objects.requireNonNull(AppConstants.getCurrentUtcTime()).toString());
                insertingBill.setTotalPrice(0);
                bills.add(insertingBill);
            }
        }
        OpenCsvWriter.getInstance().importToCsvFiles(bills, AppConstants.BILL_CSV_PATH);
        return true;
    }

    public Bill getBillById(String idBill) {
        try {
            var bills = OpenCsvReader.getInstance().parseToObject(Bill.class, AppConstants.BILL_CSV_PATH);
            var opt = bills.stream()
                    .filter(bill -> bill.getId().equals(idBill))
                    .findAny();
            return opt.orElseThrow(RuntimeException::new);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return null;
        }
    }

    public void deleteBillById(String idBill) {
        try {
            var bills = OpenCsvReader.getInstance().parseToObject(Bill.class, AppConstants.BILL_CSV_PATH);
            bills.removeIf(bill -> bill.getId().equals(idBill));
            OpenCsvWriter.getInstance().importToCsvFiles(bills, AppConstants.BILL_CSV_PATH);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
        }
    }
}
