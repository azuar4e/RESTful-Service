package es.upm.sos.biblioteca.Excepciones.Prestamos;

import java.time.LocalDate;

public class PrestamoFechaPrestamoNoCoincideException extends RuntimeException {
    public PrestamoFechaPrestamoNoCoincideException(int id, LocalDate fecha_prestamo_put, LocalDate fecha_prestamo) {
        super("El prestamos con id: "+ id +" tiene una fecha de prestamo diferente a la que se ha enviado. La fecha del prestamo es "+ fecha_prestamo +" y la fecha del prestamo que se ha enviado es "+ fecha_prestamo_put);
    }
}
