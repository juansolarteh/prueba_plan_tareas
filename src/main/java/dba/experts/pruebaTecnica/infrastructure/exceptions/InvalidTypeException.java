package dba.experts.pruebaTecnica.infrastructure.exceptions;

import dba.experts.pruebaTecnica.infrastructure.exceptions.generics.AttributeException;

public class InvalidTypeException extends AttributeException {
    public InvalidTypeException(String message) {
        super(message );
    }
}
