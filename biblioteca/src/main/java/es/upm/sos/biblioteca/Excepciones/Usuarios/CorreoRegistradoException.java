package es.upm.sos.biblioteca.Excepciones.Usuarios;

public class CorreoRegistradoException extends RuntimeException {
    public CorreoRegistradoException(){
        super("El correo ya ha sido registrado");
    }
}
