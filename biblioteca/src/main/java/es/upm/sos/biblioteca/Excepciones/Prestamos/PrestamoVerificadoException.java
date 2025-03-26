package es.upm.sos.biblioteca.Excepciones.Prestamos;

public class PrestamoVerificadoException extends RuntimeException {
    public PrestamoVerificadoException(int id) {
        super("El prestamo con id: "+ id +" ya esta verificado");
    }
}
