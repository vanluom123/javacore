package utils;

import com.opencsv.CSVWriter;
import lombok.SneakyThrows;
import model.Menu;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class OpenCsvWriter {
    private static final String[] HEADER = {"id", "type"};
    private static final String MENU_CSV = "csv/menu.csv";

    @SneakyThrows
    public static void writeToCsvFrom(Menu menu) {
        List<String[]> csvData = createCsvDataSpecial(menu);

        // loads file from resource folder
        URL resource = OpenCsvReader.class.getClassLoader().getResource(MENU_CSV);
        File file = null;
        if (resource != null) {
            file = Paths.get(resource.toURI()).toFile();
        }

        // default all fields are enclosed in double quotes
        // default separator is a comma
        if (file != null) {

            try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
                writer.writeAll(csvData);
            }
        }
    }

    private static List<String[]> createCsvDataSpecial(Menu menu) {
        List<String[]> list = new ArrayList<>();
        list.add(HEADER);
        list.add(new String[]{menu.getId(), menu.getType()});
        return list;
    }
}
