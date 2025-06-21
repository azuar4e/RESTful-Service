package es.upm.sos.biblioteca.Excepciones.Prestamos;

public class LibroNoCoincideException extends RuntimeException {
    public LibroNoCoincideException(int id, String isbn_devolucion, String isbn_prestamo) {
        super("El prestamos con id: "+ id +" tiene un libro diferente a la que se ha enviado. El ISBN del libro es "+ isbn_prestamo +" y el ISBN del libro que se ha enviado es "+ isbn_devolucion);
    }    
}
