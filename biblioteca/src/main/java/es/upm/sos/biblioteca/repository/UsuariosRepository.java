package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Usuario;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuariosRepository extends JpaRepository<Usuario, String> {

    //Obtener un usuario en base a su matricula
    @Query(value = "SELECT u FROM Usuario u WHERE u.matricula = :matricula")
    Usuario getUsuario(String matricula );

    //Query para hacer update de algun dato de usuario
   // @Query("UPDATE Usuario u SET u.matricula = :matricula, u.nombre = :nombre, u.fechaNacimiento = :fecha, u.correo = :correo")
    //int actualizarUsuario(String matricula,String nombre, @Param("fecha") String fechaNacimiento, @Param("correo") String correo);

    //Query para obtener a todos los usuarios
    @Query("SELECT u FROM Usuario u")
    Page<Usuario> getUsuarios(org.springframework.data.domain.Pageable paginable);

    Usuario findByCorreo(String correo);

    Usuario findByMatricula(String matricula);

    void deleteByMatricula(String matricula);

    
    

    
}