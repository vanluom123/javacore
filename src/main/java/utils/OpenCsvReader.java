package utils;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.Bill;
import model.Menu;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenCsvReader {

    public static OpenCsvReader getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final OpenCsvReader INSTANCE = new OpenCsvReader();
    }

    public List<Menu> parseCsvToMenu(String pathName) {
        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator(CSVParser.DEFAULT_SEPARATOR)
                .build(); // custom separator

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(pathName))
                .withCSVParser(csvParser)   // custom CSV parser
                .withSkipLines(1)           // skip the first line, header info
                .build()) {
            return reader.readAll()
                    .stream()
                    .map(data -> {
                        var menu = Menu.builder()
                                .id(data[0])
                                .type(data[1])
                                .build();
                        return menu;
                    })
                    .collect(Collectors.toList());
        } catch (IOException | CsvException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Bill> parseCSVToBill(String pathName) {
        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator(CSVParser.DEFAULT_SEPARATOR)
                .build(); // custom separator

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(pathName))
                .withCSVParser(csvParser)   // custom CSV parser
                .withSkipLines(1)           // skip the first line, header info
                .build()) {
            return reader.readAll()
                    .stream()
                    .map(data -> {
                        Bill bill;
                        try {
                            bill = Bill.builder()
                                    .id(data[0])
                                    .orderedTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(data[1]))
                                    .totalPrice(Integer.parseInt(data[2]))
                                    .build();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        return bill;
                    })
                    .collect(Collectors.toList());
        } catch (IOException | CsvException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
