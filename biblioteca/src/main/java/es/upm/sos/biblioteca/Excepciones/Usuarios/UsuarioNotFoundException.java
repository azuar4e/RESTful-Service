package es.upm.sos.biblioteca.Excepciones.Usuarios;

public class UsuarioNotFoundException extends RuntimeException{
    public UsuarioNotFoundException(String matricula) {
        super("Usuario con matricula "+ matricula +" no encontrado.");
        }
}