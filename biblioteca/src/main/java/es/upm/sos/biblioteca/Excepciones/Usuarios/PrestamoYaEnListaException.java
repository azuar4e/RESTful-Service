package es.upm.sos.biblioteca.Excepciones.Usuarios;

public class PrestamoYaEnListaException extends RuntimeException{
    public PrestamoYaEnListaException(String matricula){
        super("El prestamo ya estaba en la lista.");
    }
}
