package es.upm.sos.biblioteca.models;
import java.time.LocalDate;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Usuario")// Necesario para indicar el nombre de la tabla en la base de datos
@Data // Lombok genera automáticamente los getters, setters, equals, hashcode y toString
@NoArgsConstructor // Crea un constructor vacío
@AllArgsConstructor

public class Usuario extends RepresentationModel<Usuario>{
    @Id
    private String matricula;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String correo;

    @Column(name="fecha_nacimiento", nullable = false)
    private String fechaNacimiento;

    @Column
    private LocalDate sancion;

    @OneToMany(mappedBy = "usuario")
    private List<Prestamo> prestamos;
}
