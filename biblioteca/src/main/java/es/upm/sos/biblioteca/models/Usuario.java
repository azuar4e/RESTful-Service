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

    @Column(columnDefinition = "date default null")
    private LocalDate sancion;

    //en caso de aun no haber devuelto un libro de un prestamo pasado
    //mirar si hay libros por devolver para conceder un prestamo
    //y sancionar 1 semanilla en el momento en el que devuelva todos los libros
    @Column(columnDefinition = "integer default 0")
    private int porDevolver;

    @OneToMany(mappedBy = "usuario")
    private List<Prestamo> prestamos;
}
