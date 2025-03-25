package es.upm.sos.biblioteca.Excepciones.Usuarios;

import es.upm.sos.biblioteca.Excepciones.ErrorMessage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice//Captura excepciones de toda la aplicación y devuelve JSON
public class UsuarioExcepcionesHandler {
    
@ExceptionHandler(UsuarioNotFoundException.class)
@ResponseStatus(HttpStatus.NOT_FOUND)
ErrorMessage UsuarioNotFoundException(UsuarioNotFoundException e){
    return new ErrorMessage(e.getMessage());
}

@ExceptionHandler(UsuarioConflictException.class)
@ResponseStatus(HttpStatus.CONFLICT)
ErrorMessage UsuarioConfictException(UsuarioConflictException e){
    return new ErrorMessage(e.getMessage());
}

@ExceptionHandler(PrestamoYaEnListaException.class)
@ResponseStatus(HttpStatus.CONFLICT)
ErrorMessage PrestamoYaEnListaException(PrestamoYaEnListaException e){
    return new ErrorMessage(e.getMessage());
}
}
