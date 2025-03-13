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
    @GeneratedValue(strategy = GenerationType.IDENTITY) // valor generado por la base de datos
    @Column(name = "isbn")
    private String isbn;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "autores")
    private String autores;

    @OneToOne
    @JoinColumn(name = "fecha_prestamo", referencedColumnName = "fecha_prestamo")
    private Prestamo prestamo;

    @Column(name = "edicion")
    private String edicion;

    @Column(name = "editorial")
    private String editorial;


}
