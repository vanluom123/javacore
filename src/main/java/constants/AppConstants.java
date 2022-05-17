package constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AppConstants {
    public static final String[] MENU_HEADER_RECORD = {"id", "type"};
    public static final String[] BILL_HEADER_RECORD = {"id", "orderedTime", "totalPrice"};
    public static final String[] ITEM_HEADER_RECORD = {"id", "menuId", "name", "description", "quality", "price", "note", "type"};
    public static final String MENU_CSV_PATH = "D:\\menu.csv";
    public static final String BILL_CSV_PATH = "D:\\bill.csv";
    public static final String ITEM_CSV_PATH = "D:\\item.csv";

    public static Date getCurrentUtcTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        try {
            return localDateFormat.parse(simpleDateFormat.format(new Date()));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
