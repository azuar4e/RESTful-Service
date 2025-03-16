package es.upm.sos.biblioteca.Excepciones.Prestamos;
import es.upm.sos.biblioteca.Excepciones.ErrorMessage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice//Captura excepciones de toda la aplicación y devuelve JSON
public class PrestamosExcepciones {

    @ExceptionHandler(PrestamoNotFoundContentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage PrestamoNotFoundContentException(PrestamoNotFoundContentException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(FechaDevolucionException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage FechaDevolucionException(FechaDevolucionException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(PrestamoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage PrestamoNotFoundException(PrestamoNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(PrestamoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage PrestamoConflictException(PrestamoConflictException ex) {
        return new ErrorMessage(ex.getMessage());
    }
}