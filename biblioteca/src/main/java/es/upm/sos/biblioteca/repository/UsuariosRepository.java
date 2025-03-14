package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuariosRepository extends JpaRepository<Usuario, String> {

    //Obtener un usuario en base a su matricula
    @Query(value = "SELECT u FROM Usuario u WHERE u.matricula = :matricula")
    Usuario getUsuario(@Param("matricula") String matricula);

    //Query para hacer update de algun dato de usuario
    @Query("UPDATE Usuario u SET u.matricula = :matricula, u.nombre = :nombre, u.fechaNacimiento = :fecha, u.correo = :correo")
    int actualizarUsuario(@Param("matricula") String matricula, @Param("nombre") String nombre, @Param("fecha") String fechaNacimiento, @Param("correo") String correo);

    //Query para obtener a todos los usuarios
    @Query("SELECT u FROM Usuario u")
    Page<Usuario> getUsuarios(Pageable pageable);

    Usuario findByMatricula(String matricula);

    //Para borrar un usuario segun su matricula
    @Query(value = "DELETE Usuario u WHERE u.matricula = :matricula")
    void deleteUsuario(@Param("matricula") String matricula);
    
}