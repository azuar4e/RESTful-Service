package es.upm.sos.biblioteca.repositorios;
import es.upm.sos.biblioteca.modelos.Prestamos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamosRepository extends JpaRepository<Prestamos, Integer> {
}