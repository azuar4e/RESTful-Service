package es.upm.sos.biblioteca.Excepciones.HistorialPrestamos;

import java.time.LocalDate;

public class HistorialEntrePrestamosNull extends RuntimeException{
    public HistorialEntrePrestamosNull(String matricula, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        super("No se encontro historial de prestamos entre "+ fechaPrestamo+ "y "+fechaDevolucion+ " para el numero de matricula "+ matricula);
        }
}