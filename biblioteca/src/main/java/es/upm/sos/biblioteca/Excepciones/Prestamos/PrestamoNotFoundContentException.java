package es.upm.sos.biblioteca.Excepciones.Prestamos;

import java.time.LocalDate;

public class PrestamoNotFoundContentException extends RuntimeException {

    public PrestamoNotFoundContentException(String matricula, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        super(construirMensaje(matricula, fechaPrestamo, fechaDevolucion)); // Llamada a super() como primera instrucción
    }

    // Método auxiliar para construir el mensaje
    private static String construirMensaje(String matricula, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        if (fechaPrestamo == null && fechaDevolucion == null) {
            return "No se han encontrado prestamos para el numero de matricula: " + matricula + ".";
        } else if (fechaPrestamo == null) {
            return "No se han encontrado prestamos para el numero de matricula: " + matricula + " y la fecha de devolucion: " + fechaDevolucion + ".";
        } else if (fechaDevolucion == null) {
            return "No se han encontrado prestamos para el numero de matricula: " + matricula + " y la fecha de prestamo: " + fechaPrestamo + ".";
        } else {
            return "No se han encontrado prestamos para el numero de matricula: " + matricula + ", la fecha de prestamo: " + fechaPrestamo + " y la fecha de devolucion: " + fechaDevolucion + ".";
        }
    }

}
