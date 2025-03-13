package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuario, String> {

    
    // @Query("INSERT INTO Usuario (matricula, nombre, fechaNacimiento, correo) VALUES (':matricula', ':nombre', ':fecha', ':correo');")
    // List<Usuario> parametros(@Param("matricula") int matricula, @Param("fecha") String fechaNacimiento, @Param("nombre") String nombre, @Param("correo") String correo);

    @Query("SELECT u FROM Usuario u WHERE u.matricula = :matricula")
    List<Usuario> parametros(@Param("matricula") int matricula);

    // @Query("UPDATE ")

    @Query(value = "DELETE Usuario u WHERE u.matricula = :matricula")
    void deleteUsuario(@Param("matricula") String matricula);


    @Query("SELECT u FROM Usuario u")
    List<Usuario> obtenerTodosLosUsuarios();

}