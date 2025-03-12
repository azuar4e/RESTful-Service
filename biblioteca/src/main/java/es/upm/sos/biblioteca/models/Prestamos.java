package es.upm.sos.biblioteca.models;
import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Prestamos")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Prestamos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iId;

    @ManyToOne
    @JoinColumn(name = "matricula", referencedColumnName = "matricula")
    private Usuarios usuario;

    @OneToOne
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    private Libros libro;

    @Column(name = "fecha_prestamo")
    private Data dPrestamo;
}