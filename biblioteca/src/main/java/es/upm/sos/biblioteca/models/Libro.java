package es.upm.sos.biblioteca.models;
import jakarta.persistence.*;
import lombok.*; //Librería java para reducir la cantidad de código

@Entity
@Table(name="Libro")// Necesario para indicar el nombre de la tabla en la base de datos
@Data // Lombok genera automáticamente los getters, setters, equals, hashcode y toString
@NoArgsConstructor // Crea un constructor vacío
@AllArgsConstructor // Crea un constructor con todos los campos

public class Libro {
    @Id
    private String isbn;

    @OneToMany
    @MapsId("pId")
    @JoinColumn(name = "pId")
    private Prestamo prestamo;

    private String titulo;
    private String autores;
    private String edicion;
    private String editorial;
}
