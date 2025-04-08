package es.upm.sos.biblioteca.cliente.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PagePrestamo {
    private Usuario _embedded;
    private PageLinks _links;
    private PageMetadata page;
}
