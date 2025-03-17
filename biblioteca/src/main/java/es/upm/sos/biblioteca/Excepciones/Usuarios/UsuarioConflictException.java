package es.upm.sos.biblioteca.Excepciones.Usuarios;

public class UsuarioConflictException extends RuntimeException{
    public UsuarioConflictException(String matricula){
        super("Usuario con matricula= "+ matricula + " ya existente en la base de datos.");
    }
}
