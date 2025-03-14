package es.upm.sos.biblioteca.models;

import jakarta.persistence.*;
import lombok.*;  // Lombok para reducir código


@Entity
@Table(name = "libro")  // Nombre de la tabla en la base de datos
@Data // Lombok genera automáticamente los getters, setters, equals, hashCode, y toString
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor con todos los campos
public class Libro {

    @Id
    @Column(name = "isbn") 
    private String isbn;
    @NotNull
    private String titulo;
    @NotNull
    private String autores;
    @NotNull
    private String edicion;
    @NotNull
    private String editorial;

    // Relación OneToMany con Prestamo
   // @OneToMany(mappedBy = "libro")  // Relación inversa en la clase Prestamo
    //private List<Prestamo> prestamos;
}
