package management;

import constants.AppConstants;
import model.Bill;
import utils.OpenCsvReader;
import utils.OpenCsvWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BillManagement {
    private final List<Bill> bills;

    public BillManagement() {
        bills = new ArrayList<>();
    }

    public void showBill() {
        bills.forEach(System.out::println);
    }

    public void createOrUpdateBill(Bill bill) {
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
        OpenCsvWriter.getInstance().writeBillToCsv(bills);
    }

    public Bill getBillById(String idBill) {
        var bills = OpenCsvReader.getInstance().parseCSVToBill(AppConstants.BILL_CSV_PATH);
        var opt = bills.stream().filter(bill -> bill.getId().equals(idBill))
                .findFirst();
        return opt.orElse(null);
    }

    public void deleteBillById(String idBill) {
        var bills = OpenCsvReader.getInstance().parseCSVToBill(AppConstants.BILL_CSV_PATH);
        bills.removeIf(bill -> bill.getId().equals(idBill));
        OpenCsvWriter.getInstance().writeBillToCsv(bills);
    }
}
