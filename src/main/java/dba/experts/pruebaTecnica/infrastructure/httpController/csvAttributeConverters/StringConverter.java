package dba.experts.pruebaTecnica.infrastructure.httpController.csvAttributeConverters;

import dba.experts.pruebaTecnica.infrastructure.exceptions.NullOrEmptyException;
import org.apache.commons.csv.CSVRecord;

public class StringConverter {
    public static String convert(CSVRecord record, String column) {
        String value = record.get(column);
        if (value == null || value.isBlank()) throw new NullOrEmptyException(column);
        return value.trim();
    }
}
