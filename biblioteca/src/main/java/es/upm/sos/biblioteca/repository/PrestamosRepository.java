package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Prestamo;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PrestamosRepository extends JpaRepository<Prestamo, Integer> {

    Optional<Prestamo> findById(Integer id);
    
    @Query(value = "DELETE FROM prestamos WHERE libro_isbn = :isbn", nativeQuery = true)
    void deleteByLibroIsbn(@Param("isbn") String isbn);

    @Query(value = "SELECT * FROM prestamos WHERE usuario_matricula = :matricula", nativeQuery = true)
    Page<Prestamo> findByUsuarioMatricula(@Param("matricula") String matricula, Pageable pageable);

    @Query(value = "SELECT * FROM prestamos WHERE usuario_matricula = :matricula AND libro_isbn = :isbn", nativeQuery = true)
    Prestamo findByUsuarioMatriculaAndLibroIsbn(@Param("matricula") String matricula, @Param("isbn") String isbn);

    @Query(value = "SELECT * FROM prestamos WHERE usuario_matricula = :matricula AND fecha_devolucion = :fechaDevolucion", nativeQuery = true)
    Page<Prestamo> findByUsuarioMatriculaAndFechaDevolucion(@Param("matricula") String matricula, @Param("fechaDevolucion") LocalDate fechaDevolucion, Pageable paginable);

    @Query(value = "SELECT * FROM prestamos WHERE usuario_matricula = :matricula AND fecha_prestamo = :fechaPrestamo", nativeQuery = true)
    Page<Prestamo> findByUsuarioMatriculaAndFechaPrestamo(@Param("matricula") String matricula, @Param("fechaPrestamo") LocalDate fechaPrestamo, Pageable paginable);

    @Query(value = "SELECT * FROM prestamos WHERE usuario_matricula = :matricula AND fecha_prestamo = :fechaPrestamo AND fecha_devolucion = :fechaDevolucion", nativeQuery = true)
    Page<Prestamo> findByUsuarioMatriculaAndFechaPrestamoAndFechaDevolucion(@Param("matricula") String matricula, @Param("fechaPrestamo") LocalDate fechaPrestamo, @Param("fechaDevolucion") LocalDate fechaDevolucion, Pageable paginable);

}
