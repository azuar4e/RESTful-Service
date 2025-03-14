package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Libro;

import java.util.List;

import org.springframework.data.jpa.repository.*;



public interface LibrosRepository extends JpaRepository<Libro, String> {

    List<Libro> findByTituloContaining(String titulo);

    Libro findByIsbn(String isbn);

    void deleteByIsbn(String isbn);
}