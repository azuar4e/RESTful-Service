package es.upm.sos.biblioteca.cliente.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PageLibro {
    private Usuario _embedded;
    private PageLinks _links;
    private PageMetadata page;
}
