package es.upm.sos.biblioteca.Excepciones.HistorialPrestamos;

public class HistorialEntrePrestamosNull extends RuntimeException{
    public UsuarioNotFoundException(String matricula, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        super("No se encontro historial de prestamos entre "+ fechaPrestamo+ "y "+fechaDevolucion+ " para el numero de matricula "+ matricula);
        }
}