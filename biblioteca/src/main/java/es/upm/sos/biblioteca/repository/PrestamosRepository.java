package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Prestamo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamosRepository extends JpaRepository<Prestamo, Integer> {

    //@Query("SELECT p FROM Prestamo p WHERE p.usuario.matricula = :matricula")
    //List<Prestamo> getPrestamosMatricula(@Param("matricula") String matricula);

   // @Query("SELECT p FROM Prestamo p WHERE p.usuario.matricula = :matricula AND p.dPrestamo = :fecha")
   // List<Prestamo> getPrestamosMatriculayFecha(@Param("matricula") String matricula, @Param("fecha") String fecha);

   Prestamo findById(int id);
   
   List<Prestamo> findByMatricula(String matricula);

   Prestamo findByUsuarioMatriculaAndLibroIsbn(String matricula, String isbn);

   List<Prestamo> findByUsuarioMatriculaAndFechaDevolucion(String matricula, LocalDate fechaDevolucion);

   List<Prestamo> findByUsuarioMatriculaAndFechaPrestamo(String matricula, LocalDate fechaPrestamo);

   List<Prestamo> findByUsuarioMatriculaAndFechaPrestamoAndFechaDevolucion(String matricula, LocalDate fechaPrestamo, Date fechaDevolucion);

   @Query("UPDATE Prestamo p SET p.fechaDevolucion = :fechaDevolucion WHERE p.usuario.matricula = :matricula AND p.libro.isbn = :isbn")
   void actualizarFechaDevolucion(@Param("matricula") String matricula, @Param("isbn") String isbn, @Param("fechaDevolucion") LocalDate fechaDevolucion);

}