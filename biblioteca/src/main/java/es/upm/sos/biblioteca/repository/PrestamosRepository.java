package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Libro;
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
    
    // @Query(value = "DELETE FROM prestamos WHERE libro_isbn = :isbn", nativeQuery = true)
    // void deleteByLibroIsbn(@Param("isbn") String isbn);

    //@Query(value = "SELECT * FROM prestamos WHERE usuario_matricula = :matricula", nativeQuery = true)
    Page<Prestamo> findByUsuarioMatriculaAndDevueltoFalse(@Param("matricula") String matricula, Pageable pageable);

    // @Query(value = "SELECT * FROM prestamos WHERE usuario_matricula = :matricula AND libro_isbn = :isbn", nativeQuery = true)
    Prestamo findByUsuarioMatriculaAndLibroIsbn(@Param("matricula") String matricula, @Param("isbn") String isbn);

    // @Query(value = "SELECT * FROM prestamos WHERE usuario_matricula = :matricula AND fecha_devolucion = :fechaDevolucion", nativeQuery = true)
    Page<Prestamo> findByUsuarioMatriculaAndFechaDevolucion(@Param("matricula") String matricula, @Param("fechaDevolucion") LocalDate fechaDevolucion, Pageable paginable);

    // @Query(value = "SELECT * FROM prestamos WHERE usuario_matricula = :matricula AND fecha_prestamo = :fechaPrestamo", nativeQuery = true)
    Page<Prestamo> findByUsuarioMatriculaAndFechaPrestamo(@Param("matricula") String matricula, @Param("fechaPrestamo") LocalDate fechaPrestamo, Pageable paginable);

    // @Query(value = "SELECT * FROM prestamos WHERE usuario_matricula = :matricula AND fecha_prestamo = :fechaPrestamo AND fecha_devolucion = :fechaDevolucion", nativeQuery = true)
    Page<Prestamo> findByUsuarioMatriculaAndFechaPrestamoAndFechaDevolucion(@Param("matricula") String matricula, @Param("fechaPrestamo") LocalDate fechaPrestamo, @Param("fechaDevolucion") LocalDate fechaDevolucion, Pageable paginable);

    @Query("SELECT p FROM Prestamo p WHERE p.usuario.matricula = :matricula AND p.devuelto = false AND p.fechaDevolucion > CURRENT_DATE ORDER BY p.fechaPrestamo DESC")
    Page<Prestamo> getPrestamosActuales(@Param("matricula") String matricula, Pageable paginable);

    // @Query("SELECT p FROM Prestamo p WHERE p.usuario.matricula = :matricula AND p.devuelto = false AND p.fechaDevolucion  :fechaActual ORDER BY p.fechaPrestamo DESC")
    // Page<Prestamo> getPrestamosActuales(@Param("matricula") String matricula, @Param("fechaActual") LocalDate localdate, Pageable paginable);
     
    @Query("SELECT p FROM Prestamo p WHERE p.usuario.matricula = :matricula AND p.devuelto = true ORDER BY p.fechaDevolucion DESC")
    Page<Prestamo> getUltimosLibrosDevueltos(@Param("matricula") String matricula, Pageable pageable);

    Prestamo findByLibroIsbn(String isbn);
}
