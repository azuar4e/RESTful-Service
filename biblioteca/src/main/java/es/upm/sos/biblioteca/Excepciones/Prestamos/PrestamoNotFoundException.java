package es.upm.sos.biblioteca.Excepciones.Prestamos;

public class PrestamoNotFoundException extends RuntimeException {
    public PrestamoNotFoundException(Integer id, String matricula, String isbn) {
        super(construirMensaje(id, matricula, isbn));
    }

    public static String construirMensaje(Integer id, String matricula, String isbn) {
        if (id ==  null) {
            return "No se pudo encontrar el prestamo con matricula "+matricula+" e isbn "+isbn+".";
        } else {
            return "No se pudo encontrar el prestamo con id "+id+".";
        }
    }
}
