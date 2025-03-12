package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuarios, String> {
}