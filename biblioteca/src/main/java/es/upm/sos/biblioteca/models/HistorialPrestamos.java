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
    private int pId;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    @NotNull
    private Date fechaPrestamo;
    @NotNull
    private Date fechaDevolucion;
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