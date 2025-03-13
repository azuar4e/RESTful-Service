package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Libro;
import es.upm.sos.biblioteca.models.Prestamo;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


public interface LibrosRepository extends JpaRepository<Libro, String> {

    //query a la bbdd para seleccionar todos los libros    
   @Query(value = "SELECT l FROM Libro l")
   List<Libro> getLibros();

   //query a la bbdd para seleccionar libros filtrado por el contenido del titulo
   @Query(value = "SELECT l FROM Libro l WHERE l.titulo LIKE %:titulo%")
   List<Libro> getLibrosContenido(@Param("titulo") String titulo);

    //query a la bbdd para seleccionar libros filtrado por si estan disponibles
    //falta hacerla

    //query a la bbdd para seleccionar un libro concreto a partir de su isbn
    @Query(value = "SELECT l FROM Libro l WHERE l.isbn= :isbn")
    Libro getLibroIsbn(@Param("isbn") String isbn);

    //para POST no hace falta realizar ninguna query
    
   // @Query("UPDATE Libro l SET l.prestamo = :fecha WHERE l.prestamo IS NOT NULL AND l.id = :id")
   // void actualizarLibroPrestamo(@Param("fecha") String fecha, @Param("id") int id);

//  query para deletee
    @Query(value = "DELETE Libro l WHERE l.isbn = :isbn")
    void deleteLibro(@Param("isbn") String isbn);
}