package es.upm.sos.biblioteca.Excepciones.Prestamos;

public class PrestamoNotFoundException extends RuntimeException {
    public PrestamoNotFoundException(String matricula, String isbn) {
        super("No se pudo encontrar el prestamo con matricula "+matricula+" e isbn "+isbn.".");
    }
}
