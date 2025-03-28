package es.upm.sos.biblioteca.Excepciones.Prestamos;

import java.time.LocalDate;

public class FechasNoValidasException extends RuntimeException{
    public FechasNoValidasException(LocalDate fechaPrestamo, LocalDate fechaDevolucion){
        super("La fecha de prestamo: "+fechaPrestamo+" no puede ser posterior a la fecha de devolucion: "+fechaDevolucion);
    }
}
