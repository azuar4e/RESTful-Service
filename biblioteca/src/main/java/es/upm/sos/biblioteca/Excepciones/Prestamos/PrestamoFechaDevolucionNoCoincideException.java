package es.upm.sos.biblioteca.Excepciones.Prestamos;

import java.time.LocalDate;

public class PrestamoFechaDevolucionNoCoincideException extends RuntimeException {
    public PrestamoFechaDevolucionNoCoincideException(int id, LocalDate fecha_devolucion_put, LocalDate fecha_devolucion) {
        super("El prestamos con id: "+ id +" tiene una fecha de devolucion diferente a la que se ha enviado. La fecha de devolucion es "+ fecha_devolucion +" y la fecha de devolucion que se ha enviado es "+ fecha_devolucion_put);
    }
}
