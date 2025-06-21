package es.upm.sos.biblioteca.Excepciones.Prestamos;

public class UsuarioNoCoincideException extends RuntimeException {
    public UsuarioNoCoincideException(int id, String matricula_devolucion, String matricula_prestamo) {
        super("El prestamos con id: "+ id +" tiene un usuario diferente a la que se ha enviado. La matricula del usuario es "+ matricula_prestamo +" y la matricula del usuario que se ha enviado es "+ matricula_devolucion);
    }    
}
