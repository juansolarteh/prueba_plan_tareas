package dba.experts.pruebaTecnica.infrastructure.httpController.csvAttributeConverters;

import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;

public class ListStringConverter {

    public static List<String> convert(CSVRecord record, String column) {
        String value = record.get(column);
        if (value == null || value.isBlank()) return new ArrayList<>();
        List<String> list = new ArrayList<>();

        String delimiter1 = "|";
        String delimiter2 = " ";

        if (value.contains(delimiter1)) {
            list = splitAndTrim(value, delimiter1);
        } else if (value.contains(delimiter2)) {
            list = splitAndTrim(value, delimiter2);
        } else {
            String trimmed = value.trim();
            if (!trimmed.isEmpty()) {
                list.add(trimmed);
            }
        }
        return list;
    }

    private static List<String> splitAndTrim(String value, String delimiter) {
        String[] parts = value.split("\\" + delimiter);
        List<String> list = new ArrayList<>();
        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                list.add(trimmed);
            }
        }
        return list;
    }
}
