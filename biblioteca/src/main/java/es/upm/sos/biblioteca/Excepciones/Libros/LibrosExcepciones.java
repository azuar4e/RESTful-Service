package es.upm.sos.biblioteca.Excepciones.Libros;
import es.upm.sos.biblioteca.Excepciones.ErrorMessage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice//Captura excepciones de toda la aplicación y devuelve JSON
public class LibrosExcepciones {

//LIBRO NO ENCONTRADO
@ExceptionHandler(LibroNotFoundException.class)//Permite capturar una excepción específica y devuelve una respuesta personalizada
@ResponseStatus(HttpStatus.NOT_FOUND)//Permite indicar el código HTTP que deseamos enviar al capturar esta excepción
ErrorMessage LibroNotFoundException(LibroNotFoundException ex) {
return new ErrorMessage(ex.getMessage());
}

//CONFLICTO DE POST EN LIBRO
@ExceptionHandler(LibroConflictException.class)
@ResponseStatus(HttpStatus.CONFLICT)
ErrorMessage LibroConflictException(LibroConflictException ex) {
    return new ErrorMessage(ex.getMessage());
}

}