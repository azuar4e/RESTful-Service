package es.upm.sos.biblioteca.repository;
import es.upm.sos.biblioteca.models.Usuario;
import es.upm.sos.biblioteca.models.Prestamo;


import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.Optional;


public interface UsuariosRepository extends JpaRepository<Usuario, String> {

    //Obtener un usuario en base a su matricula
    @Query(value = "SELECT u FROM Usuario u WHERE u.matricula = :matricula")
    Usuario getUsuario(String matricula);

    //Query para hacer update de algun dato de usuario
   // @Query("UPDATE Usuario u SET u.matricula = :matricula, u.nombre = :nombre, u.fechaNacimiento = :fecha, u.correo = :correo")
    //int actualizarUsuario(String matricula,String nombre, @Param("fecha") String fechaNacimiento, @Param("correo") String correo);

    //Query para obtener a todos los usuarios
    @Query("SELECT p FROM Prestamo p WHERE p.usuario.matricula = :matricula")
    Page<Prestamo> findByPrestamosUsuarioMatricula(@Param("matricula") String matricula, org.springframework.data.domain.Pageable pageable);

    @Query("SELECT p FROM Prestamo p WHERE p.usuario.matricula = :matricula AND p.devuelto = :devuelto")
    Page<Prestamo> findByPrestamosUsuarioMatriculaDevuelto(@Param("matricula") String matricula, @Param("devuelto") boolean devuelto, org.springframework.data.domain.Pageable pageable);

    @Query("SELECT p FROM Prestamo p WHERE p.usuario.matricula = :matricula AND p.fecha_prestamo = :fecha_prestamo")
    Page<Prestamo> findByPrestamosUsuarioMatriculaAndFechaPrestamo(
        @Param("matricula") String matricula, 
        @Param("fecha_prestamo") LocalDate fecha_prestamo, 
        org.springframework.data.domain.Pageable pageable);

    @Query("SELECT p FROM Prestamo p WHERE p.usuario.matricula = :matricula AND p.fecha_devolucion = :fecha_devolucion")
    Page<Prestamo> findByPrestamosUsuarioMatriculaAndFechaDevolucion(
        @Param("matricula") String matricula, 
        @Param("fecha_devolucion") LocalDate fecha_devolucion, 
        org.springframework.data.domain.Pageable pageable);

    @Query("SELECT p FROM Prestamo p WHERE p.usuario.matricula = :matricula AND p.fecha_prestamo = :fecha_prestamo AND p.fecha_devolucion = :fecha_devolucion")
    Page<Prestamo> findByPrestamosUsuarioMatriculaAndFechaPrestamoAndFechaDevolucion(
        @Param("matricula") String matricula, 
        @Param("fecha_prestamo") LocalDate fecha_prestamo, 
        @Param("fecha_devolucion") LocalDate fecha_devolucion, 
        org.springframework.data.domain.Pageable pageable);

    Usuario findByCorreo(String correo);

    boolean existsByMatricula(String matricula);
    
    Usuario findByMatricula(String matricula);

    void deleteByMatricula(String matricula);   

//buscar un prestamo con su id que perteneza al usuario con matricula 
    @Query("SELECT p FROM Prestamo p WHERE p.id = :id AND p.usuario.matricula = :matricula")
    Optional<Prestamo> findPrestamoByIdAndUsuarioMatricula(@Param("id") int id, @Param("matricula") String matricula);
}