package es.upm.sos.biblioteca.cliente.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Libro {
    private String isbn;
    private String titulo;
    private String autores;
    private String edicion;
    private String editorial;
    private int disponibles;
    private int unidades;
    @JsonProperty("_links")
    private ResourceLink _links;
}
