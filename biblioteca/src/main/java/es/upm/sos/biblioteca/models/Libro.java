package es.upm.sos.biblioteca.models;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.*;
import lombok.*;  // Lombok para reducir código


@Entity
@Table(name = "libro")  // Nombre de la tabla en la base de datos
@Data // Lombok genera automáticamente los getters, setters, equals, hashCode, y toString
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor con todos los campos
public class Libro extends RepresentationModel<Libro>{

    @Id
    @Column(name = "isbn") 
    private String isbn;

    @Column(name = "titulo") 
    private String titulo;

    @Column(name = "autores")
    private String autores;

    @Column(name = "edicion")
    private String edicion;

    @Column(name = "editorial")
    private String editorial;
}
