package es.upm.sos.biblioteca.models;
import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*; //Librería java para reducir la cantidad de código

@Entity
@Table(name="Libros")// Necesario para indicar el nombre de la tabla en la base de datos
@Data // Lombok genera automáticamente los getters, setters, equals, hashcode y toString
@NoArgsConstructor // Crea un constructor vacío
@AllArgsConstructor // Crea un constructor con todos los campos

public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // valor generado por la base de datos
    private int id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "autores")
    private String autores;

    @OneToOne
    @Column(name = "fecha_prestamo", referencedColumnName = "fecha_prestamo")
    private String prestamo;

    @Column(name = "edicion")
    private String edicion;

    @Column(name = "editorial")
    private String editorial;

    @Column(name = "isbn")
    private String isbn;

}
