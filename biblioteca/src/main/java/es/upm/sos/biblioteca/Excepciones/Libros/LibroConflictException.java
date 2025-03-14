package es.upm.sos.biblioteca.Excepciones.Libros;

public class LibroConflictException extends RuntimeException{
    public LibroConflictException(String isbn){
        super("Libro con isbn= "+ isbn + " ya existente en la base de datos.");
    }
}
