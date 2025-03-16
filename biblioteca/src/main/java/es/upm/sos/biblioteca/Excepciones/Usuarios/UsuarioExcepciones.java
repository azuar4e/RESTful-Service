package es.upm.sos.biblioteca.Excepciones.Prestamos;
import es.upm.sos.biblioteca.Excepciones.ErrorMessage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice//Captura excepciones de toda la aplicación y devuelve JSON
public class UsuarioExcepciones {

    @ExceptionHandler(PrestamoNotFoundContentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage UsuarioNotFoundException(PrestamoNotFoundContentException ex) {
        return new ErrorMessage(ex.getMessage());
    }
}