package es.upm.sos.biblioteca.Excepciones.HistorialPrestamos;
import es.upm.sos.biblioteca.Excepciones.ErrorMessage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice//Captura excepciones de toda la aplicación y devuelve JSON
public class HistorialExcepciones {

    @ExceptionHandler(HistorialIsNull.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage HistorialIsNull(HistorialIsNull ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(HistorialEntrePrestamosNull.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage HistorialEntrePrestamosNull(HistorialEntrePrestamosNull ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(HistorialNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage HistorialNotFound(HistorialNotFound ex) {
        return new ErrorMessage(ex.getMessage());
    }
}