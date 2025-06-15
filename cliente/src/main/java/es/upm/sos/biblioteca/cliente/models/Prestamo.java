package es.upm.sos.biblioteca.cliente.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Prestamo {
    private int id;
    private Usuario usuario;
    private Libro libro;
    private LocalDate fecha_frestamo;
    private LocalDate fecha_devolucion;
    private boolean devuelto;
    private boolean verificarDevolucion;
    @JsonProperty("_links")
    private ResourceLink _links;
}