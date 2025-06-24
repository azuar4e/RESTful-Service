package es.upm.sos.biblioteca.cliente.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {
    private String matricula;
    private String nombre;
    private String correo;
    private String fecha_nacimiento;
    private LocalDate sancion;
    private int por_devolver;
    private List<Prestamo> prestamos;
    @JsonProperty("_links")
    private ResourceLink _links;
}
