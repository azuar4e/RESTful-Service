package es.upm.sos.biblioteca.cliente.models;

import java.util.List;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ResourceLink {
    private Href singleSelf;
    private List<Href> self;

    public String getFirstHref() {
        if (singleSelf != null) {
            return singleSelf.getHref();
        } else if (self != null && !self.isEmpty()) {
            return self.get(0).getHref();
        }
        return null;
    }
}
