package es.upm.sos.biblioteca.Excepciones.Prestamos;


public class PrestamoNotFoundContentException extends RuntimeException {

    public PrestamoNotFoundContentException(String matricula) {
        super("No se han encontrado prestamos para el numero de matricula: "+ matricula + ".");
    }

}
