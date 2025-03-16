package es.upm.sos.biblioteca.Excepciones.HistorialPrestamos;

public class HistorialNotFound extends RuntimeException{
    public HistorialNotFound(String matricula) {
        super("No se encontro historial de prestamos para la matricula "+ matricula);
        }
}