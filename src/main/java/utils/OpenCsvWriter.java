package utils;

import com.opencsv.CSVWriter;
import constants.AppConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import model.Bill;
import model.Item;
import model.Menu;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenCsvWriter {

    public static OpenCsvWriter getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final OpenCsvWriter INSTANCE = new OpenCsvWriter();
    }

    public void writeMenuToCsv(Menu menu) {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(AppConstants.MENU_CSV_PATH));
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            List<String[]> csvData = new ArrayList<>();
            csvData.add(AppConstants.MENU_HEADER_RECORD);
            csvData.add(new String[]{menu.getId(), menu.getType()});
            csvWriter.writeAll(csvData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeMenuToCsv(Collection<Menu> menus) {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(AppConstants.MENU_CSV_PATH));
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            LinkedList<String[]> csvData = new LinkedList<>();
            csvData.add(AppConstants.MENU_HEADER_RECORD);
            menus.forEach(menu -> csvData.add(new String[]{menu.getId(), menu.getType()}));
            csvWriter.writeAll(csvData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeBillToCsv(Collection<Bill> bills) {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(AppConstants.BILL_CSV_PATH));
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            LinkedList<String[]> csvData = new LinkedList<>();
            csvData.add(AppConstants.BILL_HEADER_RECORD);
            bills.forEach(bill -> csvData.add(new String[]{bill.getId(),
                    bill.getOrderedTime().toString(),
                    String.valueOf(bill.getTotalPrice())}));
            csvWriter.writeAll(csvData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeItemToCsv(Collection<Item> items) {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(AppConstants.ITEM_CSV_PATH));
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            LinkedList<String[]> csvData = new LinkedList<>();
            csvData.add(AppConstants.ITEM_HEADER_RECORD);
            items.forEach(item -> csvData.add(new String[]{item.getId(),
                    item.getMenuId(),
                    item.getName(),
                    item.getDescription(),
                    String.valueOf(item.getQuality()),
                    String.valueOf(item.getPrice()),
                    item.getNote(),
                    item.getType()
            }));
            csvWriter.writeAll(csvData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isGetter(Method method) {
        if (!method.getName().startsWith("get"))
            return false;
        if (method.getParameterTypes().length != 0)
            return false;
        if (void.class.equals(method.getReturnType()))
            return false;

        return true;
    }

    private boolean isSetter(Method method) {
        if (!method.getName().startsWith("set"))
            return false;
        if (method.getParameterTypes().length != 1)
            return false;

        return true;
    }

    @SneakyThrows
    public <T> void importToCSVFile(T model) {
//        Field[] fields = obj.getClass().getDeclaredFields();
//        var methods = obj.getClass().getDeclaredMethods();
//        Method m = null;
//        m = obj.getClass().getMethod("getId");
//        String id = (String) m.invoke(obj);

//        List to array
//        Foo[] array = new Foo[list.size()];
//        list.toArray(array); // fill the array

        Writer writer = Files.newBufferedWriter(Paths.get(AppConstants.MENU_CSV_PATH));

        CSVWriter csvWriter = new CSVWriter(writer,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        LinkedList<String[]> csvData = new LinkedList<>();
        csvData.add(AppConstants.MENU_HEADER_RECORD);

        var methods = model.getClass().getDeclaredMethods();
        var fields = model.getClass().getDeclaredFields();
        Stream.of(methods)
                .forEach(method -> {

                });
        String[] record = new String[255];
        int index = 0;
        for (var method : methods) {
            Method m = null;
            Object objectConvert = null;
            if (isGetter(method)) {
                m = model.getClass().getMethod(method.getName());
                try {
                    objectConvert = m.invoke(model);
                    record[index] = objectConvert.toString();
                    if (objectConvert instanceof Collection<?>) {
                        System.out.println("Yes");
                    }
                } catch (NullPointerException ex) {
                    System.out.println(ex.getMessage());
                }
                ++index;
            }
        }

        csvData.add(record);
        csvWriter.writeAll(csvData);
    }
}
