package es.upm.sos.biblioteca.Excepciones.Prestamos;

public class PrestamoVerificadoException extends RuntimeException {
    public PrestamoVerificadoException(int id) {
        super("El prestamo con id: "+ id +" no se puede verificar -> o bien ya se ha verificado o bien ya se ha devuelto o bien el prestamo aun no ha caducado");
    }
}
