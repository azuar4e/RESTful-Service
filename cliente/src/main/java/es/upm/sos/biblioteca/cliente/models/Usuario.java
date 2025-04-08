package es.upm.sos.biblioteca.cliente.models;

import java.time.LocalDate;
import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {
    private String matricula;
    private String nombre;
    private String correo;
    private String fechaNacimiento;
    private LocalDate sancion;
    private int porDevolver;
    private List<Prestamo> prestamos;
    private ResourceLink _links;
}
