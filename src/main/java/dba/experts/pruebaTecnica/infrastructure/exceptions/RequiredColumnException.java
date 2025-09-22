package dba.experts.pruebaTecnica.infrastructure.exceptions;

import dba.experts.pruebaTecnica.infrastructure.exceptions.generics.AttributeException;

public class RequiredColumnException extends AttributeException {
    public RequiredColumnException(String column) {
        super("El archivo CSV debe contener la columna: " + column );
    }
}
