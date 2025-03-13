package es.upm.sos.biblioteca.models;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Prestamo")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iId;

    @ManyToOne
    @JoinColumn(name = "matricula", referencedColumnName = "matricula")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    private Libro libro;

    @Column(name = "fecha_prestamo")
    private Date dPrestamo;
}