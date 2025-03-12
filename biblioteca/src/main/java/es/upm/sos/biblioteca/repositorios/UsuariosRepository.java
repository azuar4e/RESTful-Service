package es.upm.sos.biblioteca.repositorios;
import es.upm.sos.biblioteca.modelos.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuarios, String> {
}