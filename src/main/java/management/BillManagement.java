package management;

import constants.AppConstants;
import model.Bill;
import model.OrderedItem;
import utils.OpenCsvReader;
import utils.OpenCsvWriter;
import validate.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BillManagement {
    private final List<Bill> bills;

    public BillManagement() {
        bills = new ArrayList<>();
    }

    public void showBill() {
        OpenCsvReader.getInstance().parseCsvToObjectUsingAnnotation(Bill.class, AppConstants.BILL_CSV_PATH)
                .forEach(System.out::println);
    }

    public void createOrUpdateOrderItem(OrderedItem orderedItem) {

    }

    public void createOrUpdateBill(Bill bill) {
        if (Validation.getInstance().isInvalidId(bill))
            return;

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
                var insertingBill = Bill.builder()
                        .id(UUID.randomUUID().toString())
                        .orderedTime(AppConstants.getCurrentUtcTime())
                        .totalPrice(0)
                        .build();
                bills.add(insertingBill);
            }
        }
        OpenCsvWriter.getInstance().importToCsvFiles(bills, AppConstants.BILL_CSV_PATH);
    }

    public Bill getBillById(String idBill) {
        var bills = OpenCsvReader.getInstance().parseCsvToObjectUsingAnnotation(Bill.class, AppConstants.BILL_CSV_PATH);
        var opt = bills.stream().filter(bill -> bill.getId().equals(idBill))
                .findFirst();
        return opt.orElse(null);
    }

    public void deleteBillById(String idBill) {
        var bills = OpenCsvReader.getInstance().parseCsvToObjectUsingAnnotation(Bill.class, AppConstants.BILL_CSV_PATH);
        bills.removeIf(bill -> bill.getId().equals(idBill));
        OpenCsvWriter.getInstance().importToCsvFiles(bills, AppConstants.BILL_CSV_PATH);
    }
}
