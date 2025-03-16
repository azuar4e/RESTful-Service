package es.upm.sos.biblioteca.Excepciones.Prestamos;

import java.time.LocalDate;

public class PrestamoNotFoundContentException extends RuntimeException {

    public PrestamoNotFoundContentException(String matricula, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        if (fechaPrestamo == null && fechaDevolucion == null) {
            super("No se han encontrado prestamos para el numero de matricula: "+ matricula + ".");
        } else if (fechaPrestamo == null) {
            super("No se han encontrado prestamos para el numero de matricula: "+ matricula + " y la fecha de devolucion: "+ fechaDevolucion + ".");
        } else if (fechaDevolucion == null) {
            super("No se han encontrado prestamos para el numero de matricula: "+ matricula + " y la fecha de prestamo: "+ fechaPrestamo + ".");
        } else {
            super("No se han encontrado prestamos para el numero de matricula: "+ matricula + " y la fecha de prestamo: "+ fechaPrestamo + " y la fecha de devolucion: "+ fechaDevolucion + ".");
        }
    }

}
