package es.upm.sos.biblioteca.models;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fecha_prestamo;

    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDate fecha_devolucion;

    @Column(columnDefinition = "boolean default false")
    private boolean devuelto;

    @Column(columnDefinition = "boolean default false")
    private boolean verificarDevolucion;
}