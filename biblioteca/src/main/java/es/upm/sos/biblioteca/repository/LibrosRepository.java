package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Libro;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;



public interface LibrosRepository extends JpaRepository<Libro, String> {

    List<Libro> findByTituloContaining(String titulo);

    Libro findByIsbn(String isbn);

    void deleteByIsbn(String isbn);

    @Query(value = "SELECT * FROM libro WHERE (isbn NOT IN (SELECT libro_id FROM prestamo)) AND titulo LIKE CONCAT('%', :contenido, '%')", nativeQuery = true)
    Page<Libro> findLibrosContenido(@Param("contenido") String contenido, Pageable pageable);


    @Query(value = "SELECT * FROM libro WHERE isbn NOT IN (SELECT libro_id FROM prestamo)", nativeQuery = true)
    Page<Libro> findLibrosDisponibles(Pageable paginable);
}