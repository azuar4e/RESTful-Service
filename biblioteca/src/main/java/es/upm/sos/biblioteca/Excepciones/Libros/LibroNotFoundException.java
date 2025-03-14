package es.upm.sos.biblioteca.Excepciones.Libros;

public class LibroNotFoundException extends RuntimeException{
    public LibroNotFoundException(String isbn) {
        super("Libro con id "+ isbn +" no encontrado.");
        }
}
