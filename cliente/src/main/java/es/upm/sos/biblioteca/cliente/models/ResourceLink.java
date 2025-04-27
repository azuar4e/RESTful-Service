package es.upm.sos.biblioteca.cliente.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ResourceLink {
    @JsonProperty("self")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Href> self;

    public String getFirstHref() {
        return (self != null && !self.isEmpty()) ? self.get(0).getHref() : null;
    }
}