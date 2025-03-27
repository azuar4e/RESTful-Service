package es.upm.sos.biblioteca.Excepciones.Prestamos;

public class UsuarioSancionadoException extends RuntimeException {
    public UsuarioSancionadoException(String matricula) {
        super("El usuario con la matricula "+matricula+" está sancionado.\n");
    }   
}
