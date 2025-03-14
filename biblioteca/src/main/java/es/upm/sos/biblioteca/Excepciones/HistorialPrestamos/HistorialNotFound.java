package es.upm.sos.biblioteca.Excepciones.HistorialPrestamos;

public class HistorialNotFound extends RuntimeException{
    public UsuarioNotFoundException(String matricula) {
        super("No se encontro historial de prestamos para la matricula "+ matricula);
        }
}