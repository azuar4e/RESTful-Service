package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Prestamos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamosRepository extends JpaRepository<Prestamos, Integer> {

    @Query("SELECT p FROM Prestamos p WHERE p.usuario.matricula = :matricula")
    List<Prestamo> getPrestamosMatricula(@Param("matricula") int matricula);

    @Query("SELECT p FROM Prestamos p WHERE p.usuario.matricula = :matricula AND p.dPrestamo = :fecha")
    List<Prestamo> getPrestamosMatriculayFecha(@Param("matricula") int matricula, @Param("fecha") String fecha);
    
}