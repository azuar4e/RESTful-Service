package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrosRepository extends JpaRepository<Libros, String> {

    //query a la bbdd para seleccionar todos los libros    
   @Query(value = "SELECT l FROM Libros l")
   List<Libros> getLibros();

   //query a la bbdd para seleccionar libros filtrado por el contenido del titulo
   @Query(value = "SELECT l FROM Libros l WHERE l.titulo LIKE %:titulo%")
   List<Libros> getLibrosContenido(@Param("titulo") String titulo);

    //query a la bbdd para seleccionar libros filtrado por si estan disponibles
    //falta hacerla

    //query a la bbdd para seleccionar un libro concreto a partir de su isbn
    @Query(value = "SELECT l FROM LIBROS l WHERE l.isbn= :isbn")
    Libro getLibroIsbn(@Param("isbn") String isbn);

    //para POST no hace falta realizar ninguna query
    
    @Query("UPDATE Libro l SET l.prestamo = :fecha WHERE l.prestamo IS NOT NULL AND l.id = :id")
    void actualizarLibroPrestamo(@Param("fecha") String fecha, @Param("id") int id);

//  query para deletee
    @Query(value = "DELETE Libro l WHERE l.isbn = :isbn")
    void deleteLibro(@Param("isbn") String isbn);
}