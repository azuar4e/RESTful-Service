package es.upm.sos.biblioteca.Excepciones.HistorialPrestamos;

import java.time.LocalDate;

public class HistorialIsNull extends RuntimeException{
    public HistorialIsNull(String matricula, LocalDate fechaDevolucion) {
        super("No hay historial de prestamos para la matricula: "+ matricula + "fecha: "+ fechaDevolucion);
        }
}