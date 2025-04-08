package es.upm.sos.biblioteca.cliente.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PageMetadata {
    private int size;
    private int totalElements;
    private int totalPages;
    private int number;
}
