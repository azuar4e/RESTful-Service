package es.upm.sos.biblioteca.cliente.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PageLinks {
    private Href first;
    private Href self;
    private Href next;
    private Href last;
}
