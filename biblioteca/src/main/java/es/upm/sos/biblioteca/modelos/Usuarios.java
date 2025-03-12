package es.upm.sos.biblioteca.modelos;
import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Usuarios")// Necesario para indicar el nombre de la tabla en la base de datos
@Data // Lombok genera automáticamente los getters, setters, equals, hashcode y toString
@NoArgsConstructor // Crea un constructor vacío
@AllArgsConstructor

public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "matricula")
    private String matricula;

    @OneToOne
    @JoinColumn(name = "fecha", referencedColumnName = "fecha_prestamo")
    private Prestamos prestamo;

    @Column(name = "correo")
    private String correo;
}
