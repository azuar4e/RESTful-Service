package es.upm.sos.biblioteca.models;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Prestamo")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class HistorialPrestamos {
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

}