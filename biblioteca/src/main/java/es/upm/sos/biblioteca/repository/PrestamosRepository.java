package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Prestamo;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamosRepository extends JpaRepository<Prestamo, Integer> {

   Prestamo findById(int id);
   
   Page<Prestamo> findByUsuarioMatricula(String matricula, Pageable paginable);

   Prestamo findByUsuarioMatriculaAndLibroIsbn(String matricula, String isbn);

   Page<Prestamo> findByUsuarioMatriculaAndFechaDevolucion(String matricula, LocalDate fechaDevolucion, Pageable paginable);

   Page<Prestamo> findByUsuarioMatriculaAndFechaPrestamo(String matricula, LocalDate fechaPrestamo, Pageable paginable);

   Page<Prestamo> findByUsuarioMatriculaAndFechaPrestamoAndFechaDevolucion(String matricula, LocalDate fechaPrestamo, LocalDate fechaDevolucion, Pageable paginable);

   void deleteById(String isbn);

}