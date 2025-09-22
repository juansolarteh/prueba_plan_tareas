package dba.experts.pruebaTecnica.infrastructure.exceptions;

import dba.experts.pruebaTecnica.infrastructure.exceptions.generics.AttributeException;

public class NullOrEmptyException extends AttributeException {
    public NullOrEmptyException(String attributeName) {
        super("El atributo " + attributeName + " no puede ser nulo o vacío." );
    }
}
