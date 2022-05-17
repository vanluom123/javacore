package utils;

import com.opencsv.CSVWriter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Stream;

@Log4j2
public class OpenCsvWriter {

    public static OpenCsvWriter getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final OpenCsvWriter INSTANCE = new OpenCsvWriter();
    }

    public <T> void importToCsvFiles(Collection<T> models, String pathName) {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(pathName));
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END)
        ) {

            LinkedList<String[]> csvData = new LinkedList<>();
            Method[] methods;
            Field[] fields;
            String[] listFields;
            String[] records = new String[0];
            boolean isInitialized = false;
            for (var model : models) {
                methods = model.getClass().getDeclaredMethods();
                fields = model.getClass().getDeclaredFields();
                if (!isInitialized) {
                    listFields = Stream.of(fields)
                            .filter(field -> !Collection.class.isAssignableFrom(field.getType()))
                            .map(Field::getName).toArray(String[]::new);
                    csvData.add(listFields);
                    records = new String[listFields.length];
                    isInitialized = true;
                }
                for (var method : methods) {
                    Method m = null;
                    Object obj = null;
                    if (MethodExtensions.isGetter(method)
                            && !Collection.class.isAssignableFrom(method.getReturnType())) {
                        try {
                            m = model.getClass().getMethod(method.getName());
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }

                        try {
                            if (m != null) {
                                obj = m.invoke(model);
                            }
                            var uppercaseMethodName = method.getName().toUpperCase().substring(3);
                            var value = Stream.of(csvData.get(0))
                                    .filter(s -> uppercaseMethodName.equals(s.toUpperCase()))
                                    .findAny().get();
                            var idx = 0;
                            for (int i = 0; i < csvData.get(0).length; ++i) {
                                if (csvData.get(0)[i].equals(value)) {
                                    idx = i;
                                    break;
                                }
                            }
                            records[idx] = String.valueOf(obj);
                        } catch (NullPointerException | InvocationTargetException | IllegalAccessException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                // create copy string array from records preventing csvData reference to records
                var copyRecords = Stream.of(records)
                        .toArray(String[]::new);
                csvData.add(copyRecords);
            }

            csvWriter.writeAll(csvData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
