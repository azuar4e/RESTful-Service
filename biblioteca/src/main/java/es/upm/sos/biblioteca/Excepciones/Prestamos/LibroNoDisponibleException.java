package es.upm.sos.biblioteca.Excepciones.Prestamos;

public class LibroNoDisponibleException extends RuntimeException {
    public LibroNoDisponibleException(String isbn) {
        super("El libro con el ISBN "+isbn+" no esta disponible.\n");
    }
}
