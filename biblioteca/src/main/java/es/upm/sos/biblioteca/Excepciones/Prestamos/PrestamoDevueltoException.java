package es.upm.sos.biblioteca.Excepciones.Prestamos;

public class PrestamoDevueltoException extends RuntimeException {
    public PrestamoDevueltoException(int id) {
        super("El prestamo con id: "+ id +" ya ha sido devuelto");
    }
}
