package es.upm.sos.biblioteca.cliente.models;

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
    private ResourceLink _links;
}
