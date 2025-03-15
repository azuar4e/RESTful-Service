package es.upm.sos.biblioteca.Excepciones.HistorialPrestamos;

public class HistorialIsNull extends RuntimeException{
    public UsuarioIsNull(String matricula, LocalDate fechaDevolucion) {
        super("No hay historial de prestamos para la matricula: "+ matricula + "fecha: "+ fechaDevolucion);
        }
}