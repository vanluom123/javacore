package utils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenCsvReader {

    public static OpenCsvReader getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final OpenCsvReader INSTANCE = new OpenCsvReader();
    }
    public <T> List<T> parseCsvToObjectUsingAnnotation(Class<? extends T> type, String csvFile) {
        try (Reader reader = new FileReader(csvFile)) {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withType(type)
                    .withSkipLines(1) // skip header
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
