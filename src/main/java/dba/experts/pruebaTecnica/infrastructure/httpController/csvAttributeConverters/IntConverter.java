package dba.experts.pruebaTecnica.infrastructure.httpController.csvAttributeConverters;

import dba.experts.pruebaTecnica.infrastructure.exceptions.InvalidTypeException;
import dba.experts.pruebaTecnica.infrastructure.exceptions.NullOrEmptyException;
import org.apache.commons.csv.CSVRecord;

public class IntConverter {
    public static int convert(CSVRecord record, String column) {
        String value = record.get(column);
        if (value == null || value.isBlank()) throw new NullOrEmptyException(column);
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new InvalidTypeException("El valor de '" + column + "' debe ser un número entero.");
        }
    }
}
