package es.upm.sos.biblioteca.Excepciones.Prestamos;

public class PrestamoNotFoundException extends RuntimeException {
    public PrestamoNotFoundException(int id, String matricula, String isbn) {
        if (id ==  null) {
            super("No se pudo encontrar el prestamo con matricula "+matricula+" e isbn "+isbn+".");
        } else {
            super("No se pudo encontrar el prestamo con id "+id+".");
        }
    }
}
