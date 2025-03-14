
package es.upm.sos.biblioteca.Excepciones.Prestamos;

public class FechaDevolucionException extends RuntimeException {
    public FechaDevolucionException(String fechaActual, String fechaDevolucionActual) {
        super("No se puede actualizar la fecha de devolucion de un prestamo ya finalizado.\n"+
                "\t[+] Fecha Actual: "+ fechaActual+"\n"+
                "\t[+] Fecha de Devolucion Actual: "+ fechaDevolucionActual+"\n");
    }
}
