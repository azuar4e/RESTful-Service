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
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorMessage FechaDevolucionException(FechaDevolucionException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(PrestamoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage PrestamoNotFoundException(PrestamoNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(PrestamoConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorMessage PrestamoConflictException(PrestamoConflictException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(PrestamoVerificadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorMessage PrestamoVerificadoException(PrestamoVerificadoException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(LibroNoDisponibleException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorMessage LibroNoDisponibleException(LibroNoDisponibleException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(UsuarioDevolucionesPendientesException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorMessage UsuarioDevolucionesPendientesException(UsuarioDevolucionesPendientesException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(UsuarioSancionadoException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorMessage UsuarioSancionadoException(UsuarioSancionadoException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(PrestamoDevueltoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorMessage PrestamoDevueltoException(PrestamoDevueltoException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(PrestamoFechaPrestamoNoCoincideException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage PrestamoFechaPrestamoNoCoincideException(PrestamoFechaPrestamoNoCoincideException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(PrestamoFechaDevolucionNoCoincideException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage PrestamoFechaDevolucionNoCoincideException(PrestamoFechaDevolucionNoCoincideException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(UsuarioNoCoincideException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage UsuarioNoCoincideException(UsuarioNoCoincideException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(LibroNoCoincideException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage LibroNoCoincideException(LibroNoCoincideException ex) {
        return new ErrorMessage(ex.getMessage());
    }
}