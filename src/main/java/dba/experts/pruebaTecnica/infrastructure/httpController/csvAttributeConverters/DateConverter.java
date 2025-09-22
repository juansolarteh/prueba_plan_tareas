package dba.experts.pruebaTecnica.infrastructure.httpController.csvAttributeConverters;

import dba.experts.pruebaTecnica.infrastructure.exceptions.InvalidTypeException;
import dba.experts.pruebaTecnica.infrastructure.exceptions.NullOrEmptyException;
import org.apache.commons.csv.CSVRecord;

import java.util.Date;

public class DateConverter {
    private static String format = "YYYY-MM-DDTHH:MM";
    public static Date convert(CSVRecord record, String column) {
        String value = record.get(column);
        if (value == null || value.isBlank()) throw new NullOrEmptyException(column);
        try {
            return java.sql.Timestamp.valueOf(value.replace("T", " ") + ":00");
        } catch (IllegalArgumentException e) {
            throw new InvalidTypeException("El valor de '" + column + "' debe tener el formato " + format + ".");
        }
    }
}
