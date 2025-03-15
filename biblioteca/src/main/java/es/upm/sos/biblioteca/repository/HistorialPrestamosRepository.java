package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.HistorialPrestamos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamosRepository extends JpaRepository<HistorialPrestamos, Integer> {

    List<HistorialPrestamos> findByMatricula(String matricula);

    List<HistorialPrestamos> findByUsuarioMatriculaAndFechaDevolucion(String matricula, LocalDate fechaDevolucion);
 
    List<HistorialPrestamos> findByUsuarioMatriculaAndFechaPrestamo(String matricula, LocalDate fechaPrestamo);
 
    List<HistorialPrestamos> findByUsuarioMatriculaAndFechaPrestamoAndFechaDevolucion(String matricula, LocalDate fechaPrestamo, LocalDate fechaDevolucion);

    @Query("SELECT p FROM HistorialPrestamos p WHERE p.usuario.matricula = :matricula ORDER BY p.fechaDevolucion DESC")
    List<HistorialPrestamos> getUltimosLibrosDevueltos(@Param("matricula") String matricula, Pageable pageable);
    
}