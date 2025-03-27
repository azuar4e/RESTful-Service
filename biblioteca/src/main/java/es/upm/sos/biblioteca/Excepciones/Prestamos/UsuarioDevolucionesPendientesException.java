package es.upm.sos.biblioteca.Excepciones.Prestamos;

public class UsuarioDevolucionesPendientesException extends RuntimeException {
    public UsuarioDevolucionesPendientesException(String matricula) {
        super("El usuario con la matricula "+matricula+" tiene prestamos pendientes de devolucion.\n");
    }
}
