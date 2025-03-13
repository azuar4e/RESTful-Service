package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuario, String> {
}