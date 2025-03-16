package es.upm.sos.biblioteca.Excepciones.Prestamos;

public class PrestamoConflictException extends RuntimeException {
    public PrestamoConflictException(int id) {
        super("El prestamo con id: "+ id +" ya existe");
    }
}
