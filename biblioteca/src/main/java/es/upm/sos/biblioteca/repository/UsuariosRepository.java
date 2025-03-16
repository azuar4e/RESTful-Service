package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Usuario;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuariosRepository extends JpaRepository<Usuario, String> {

    //Obtener un usuario en base a su matricula
    @Query(value = "SELECT u FROM Usuario u WHERE u.matricula = :matricula")
    Usuario getUsuario(String matricula );

    //Query para hacer update de algun dato de usuario
   // @Query("UPDATE Usuario u SET u.matricula = :matricula, u.nombre = :nombre, u.fechaNacimiento = :fecha, u.correo = :correo")

    //Query para obtener a todos los usuarios
    @Query("SELECT u FROM Usuario u")
    Page<Usuario> getUsuarios(org.springframework.data.domain.Pageable paginable);

    Usuario findByMatricula(String matricula);

    //Para borrar un usuario segun su matricula
    @Query(value = "DELETE Usuario u WHERE u.matricula = :matricula")
    void deleteUsuario(@Param("matricula") String matricula);
    
}