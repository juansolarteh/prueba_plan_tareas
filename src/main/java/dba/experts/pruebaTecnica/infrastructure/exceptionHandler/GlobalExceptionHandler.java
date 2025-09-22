package dba.experts.pruebaTecnica.infrastructure.exceptionHandler;

import dba.experts.pruebaTecnica.infrastructure.exceptions.generics.AttributeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<PropertyErrorDTO>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<PropertyErrorDTO> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.add(new PropertyErrorDTO(error.getField(), error.getDefaultMessage()))
        );
        
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AttributeException.class)
    public ResponseEntity<UniqueErrorDTO> handleAttributeException(AttributeException ex) {
        UniqueErrorDTO response = new UniqueErrorDTO(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<UniqueErrorDTO> handleRuntimeException(RuntimeException ex) {
        logger.error(ex.getMessage(), ex);
        UniqueErrorDTO response = new UniqueErrorDTO("Error interno del servidor");
        return ResponseEntity.internalServerError().body(response);
    }
}
