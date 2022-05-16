package utils;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.SneakyThrows;
import model.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class OpenCsvReader {

    @SneakyThrows({IOException.class, CsvException.class, URISyntaxException.class})
    private static void readCsvWithEmbeddedSpecial() {

        // loads file from resource folder
        URL resource = OpenCsvReader.class.getClassLoader().getResource("csv/menu.csv");
        File file = null;
        if (resource != null) {
            file = Paths.get(resource.toURI()).toFile();
        }

        List<String[]> r = null;
        if (file != null) {
            try (CSVReader reader = new CSVReader(new FileReader(file))) {
                r = reader.readAll();
            }
        }

        // print result
        int listIndex = 0;
        for (String[] arrays : r) {
            System.out.println("\nString[" + listIndex++ + "] : " + Arrays.toString(arrays));

            int index = 0;
            for (String array : arrays) {
                System.out.println(index++ + " : " + array);
            }
        }
    }

    public static List<Menu> readCsvToObject(String fileName) {
        List<Menu> beans = null;
        try {
            // loads file from resource folder
            URL resource = OpenCsvReader.class.getClassLoader().getResource("csv/menu.csv");
            File file = null;
            if (resource != null) {
                file = Paths.get(resource.toURI()).toFile();
            }

            if (file != null) {
                beans = new CsvToBeanBuilder(new FileReader(file))
                        .withType(Menu.class)
                        .withSkipLines(1)
                        .build()
                        .parse();
            }
        } catch (FileNotFoundException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return beans;
    }

    @SneakyThrows({IOException.class, CsvException.class})
    private static List<String[]> readCsvFileCustomSeparator(String fileName, char separator) {

        List<String[]> r;

        // custom separator
        CSVParser csvParser = new CSVParserBuilder().withSeparator(separator).build();
        try (CSVReader reader = new CSVReaderBuilder(
                new FileReader(fileName))
                .withCSVParser(csvParser)   // custom CSV parser
                .withSkipLines(1)           // skip the first line, header info
                .build()) {
            r = reader.readAll();
        }
        return r;

    }

    @SneakyThrows({IOException.class, CsvException.class})
    private static List<String[]> readCsvFile(String fileName) {

        List<String[]> r;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            r = reader.readAll();
        }

        // read line by line
        /*try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                r.add(lineInArray);
            }
        }*/

        return r;

    }
}
