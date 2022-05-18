package utils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenCsvReader {

    public static OpenCsvReader getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final OpenCsvReader INSTANCE = new OpenCsvReader();
    }

    public <T> List<T> parseToObject(Class<? extends T> type, String csvFile) {
        try (Reader reader = Files.newBufferedReader(Path.of(csvFile))) {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withType(type)
                    .withSkipLines(1) // skip header
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            return null;
        }
    }
}
