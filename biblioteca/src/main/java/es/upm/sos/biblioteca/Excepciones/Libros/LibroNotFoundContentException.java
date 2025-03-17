package es.upm.sos.biblioteca.Excepciones.Libros;

public class LibroNotFoundContentException extends RuntimeException{
    public LibroNotFoundContentException(String contenido){
        super("no existen libros con el titulo que contenga '" + contenido + "'");
            }

}
