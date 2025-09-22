package dba.experts.pruebaTecnica.infrastructure.exceptions;

import dba.experts.pruebaTecnica.infrastructure.exceptions.generics.AttributeException;

public class EmptyFileException extends AttributeException {
    public EmptyFileException() {
        super("El archivo está vacío." );
    }
}
