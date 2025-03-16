package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Prestamo;

import java.util.List;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.*;

@Repository
public interface PrestamosRepository extends JpaRepository<Prestamo, Integer> {

   Prestamo findById(int id);
   
   List<Prestamo> findByMatricula(String matricula);

   Prestamo findByUsuarioMatriculaAndLibroIsbn(String matricula, String isbn);

   List<Prestamo> findByUsuarioMatriculaAndFechaDevolucion(String matricula, LocalDate fechaDevolucion);

   List<Prestamo> findByUsuarioMatriculaAndFechaPrestamo(String matricula, LocalDate fechaPrestamo);

   List<Prestamo> findByUsuarioMatriculaAndFechaPrestamoAndFechaDevolucion(String matricula, LocalDate fechaPrestamo, LocalDate fechaDevolucion);

   void deleteById(String isbn);

}