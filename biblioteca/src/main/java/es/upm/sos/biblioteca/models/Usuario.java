package es.upm.sos.biblioteca.models;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Usuario")// Necesario para indicar el nombre de la tabla en la base de datos
@Data // Lombok genera automáticamente los getters, setters, equals, hashcode y toString
@NoArgsConstructor // Crea un constructor vacío
@AllArgsConstructor

public class Usuario {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String matricula;

    @OneToMany
    @MapsId("pId")
    @JoinColumn(name = "pId")
    private List<Prestamo> prestamo;
    private String nombre;    
    private String correo;
    private String fechaNacimiento;
}
