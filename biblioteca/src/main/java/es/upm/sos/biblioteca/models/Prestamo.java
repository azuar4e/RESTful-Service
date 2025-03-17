package es.upm.sos.biblioteca.models;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Prestamo")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Prestamo extends RepresentationModel<Prestamo> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;
    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDate fechaDevolucion;

   // @OneToOne
    //@MapsId("matricula")
    //@JoinColumn(name = "matricula")
    //private Usuario usuario;

   // @OneToOne
    //@MapsId("isbn")
    //@JoinColumn(name = "isbn")
    //private Libro libro;

    // @Column(name = "fecha_prestamo")
    // private Date dPrestamo;
}