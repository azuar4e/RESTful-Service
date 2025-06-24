package es.upm.sos.biblioteca.services;


import java.util.Optional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoConflictException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.FechaDevolucionException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundContentException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.CorreoRegistradoException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.UsuarioConflictException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.UsuarioNotFoundException;
import es.upm.sos.biblioteca.models.Usuario;
import es.upm.sos.biblioteca.models.Prestamo;
import es.upm.sos.biblioteca.models.Libro;
import es.upm.sos.biblioteca.repository.PrestamosRepository;
import es.upm.sos.biblioteca.repository.UsuariosRepository;
import jakarta.transaction.Transactional;
import lombok.*;

@Service //Marcamos la clase como componente de servicio
@AllArgsConstructor

public class ServicioUsuarios{

    @Autowired

    //repositorio al que llamamos para realizar las querys
    private final UsuariosRepository repository;
    private final PrestamosRepository repositoryprestamos;
    

     //metodo para actualizar/guardar un usuario en la bbdd
    public Usuario postUsuario(Usuario u){
        Usuario esta = repository.getUsuario(u.getMatricula());
        if(esta!=null){throw new UsuarioConflictException(esta.getMatricula()); }
        if(repository.findByCorreo(u.getCorreo())!=null) { throw new CorreoRegistradoException(); }
        return repository.save(u);
    }

    //Metodo para obtener los datos de un usuario a partir de su matricula
    public Usuario getUsuario(String matricula){
        Usuario user = repository.getUsuario(matricula);
        if(user==null){ throw new UsuarioNotFoundException(matricula);  }
        return user;
    }

    public Page<Prestamo> getPrestamosMatricula(String matricula, int page, int size){
        if (!repository.existsByMatricula( matricula)) {throw new UsuarioNotFoundException(matricula);}

        Pageable paginable = PageRequest.of(page, size);
        return repository.findByPrestamosUsuarioMatricula(matricula, paginable);
    }

    public Page<Prestamo> getHistorico(String matricula, boolean devuelto, int page, int size) {
        if (!repository.existsByMatricula( matricula)) {throw new UsuarioNotFoundException(matricula);}
        Pageable paginable = PageRequest.of(page, size);
        return repository.findByPrestamosUsuarioMatriculaDevuelto(matricula, devuelto, paginable);
    }

    public Page<Prestamo> getPrestamosPorFechaPrestamo(String matricula, LocalDate fecha_prestamo, int page, int size) {
        if (!repository.existsByMatricula( matricula)) {throw new UsuarioNotFoundException(matricula);}
        Pageable paginable = PageRequest.of(page, size);
        return repository.findByPrestamosUsuarioMatriculaAndFechaPrestamo(matricula, fecha_prestamo, paginable);
    }

    public Page<Prestamo> getPrestamosPorFechaDevolucion(String matricula, LocalDate fecha_devolucion, int page, int size) {
        if (!repository.existsByMatricula( matricula)) {throw new UsuarioNotFoundException(matricula);}
        Pageable paginable = PageRequest.of(page, size);
        return repository.findByPrestamosUsuarioMatriculaAndFechaDevolucion(matricula, fecha_devolucion, paginable);
    }

    public Page<Prestamo> getPrestamosPorDobleFiltrado(String matricula, LocalDate fecha_prestamo, LocalDate fecha_devolucion, int page, int size) {
        if (!repository.existsByMatricula( matricula)) {throw new UsuarioNotFoundException(matricula);}
        Pageable paginable = PageRequest.of(page, size);
        return repository.findByPrestamosUsuarioMatriculaAndFechaPrestamoAndFechaDevolucion(matricula, fecha_prestamo, fecha_devolucion, paginable);
    }

    
    //Metodo para obtener todos los usuarios | optional se usa para valores posibles null
    public Page<Usuario> getUsuarios(int page, int size){
        Pageable paginable = PageRequest.of(page,size);
        return repository.findAll(paginable);
    }

    //put
    @Transactional
    public Usuario actualizarUsuario(String matricula, Usuario usuarioNuevo) {
       // Optional<Usuario> usuarioExistente = Optional.of(repository.findByMatricula(matricula));
        if (!repository.existsByMatricula(matricula)) { throw new UsuarioNotFoundException(matricula);  }
        else{
            Usuario usuario = repository.findByMatricula(matricula);
            usuario.setNombre(usuarioNuevo.getNombre());
            usuario.setCorreo(usuarioNuevo.getCorreo());
            usuario.setFecha_nacimiento(usuarioNuevo.getFecha_nacimiento());
            usuario.setSancion(usuarioNuevo.getSancion());

            return repository.save(usuario);
        }
    }

    //metodo delete
   @Transactional
    public void deleteUsuario(String matricula) {
        if (!repository.existsByMatricula(matricula)) {
            throw new UsuarioNotFoundException(matricula);
        }
        repository.deleteByMatricula(matricula);
    }

    @Transactional//mirarlo
    public void deletePrestamo(String matricula, Integer id){
        Usuario user = repository.getUsuario(matricula);
         if(user==null){  throw new UsuarioNotFoundException(matricula); }
        boolean error = true;
        List<Prestamo> prestamosexistente = repository.getUsuario(matricula).getPrestamos();
        for(int x=0; x <prestamosexistente.size(); x++){
            if (prestamosexistente.get(x).getId()==id){
                prestamosexistente.remove(x);
                user.setPrestamos(prestamosexistente);
                error = false;
            }
        }
        if(error){
            throw new PrestamoNotFoundException(id, null, null);
        }
    }

    //ampliar un prestamo del usuario
    public Prestamo ampliarPrestamo(String matricula, int id){
        
        if (!repository.existsByMatricula(matricula)) { throw new UsuarioNotFoundException(matricula); }

        Optional<Prestamo> prestamo = repository.findPrestamoByIdAndUsuarioMatricula(id,matricula);
        if (prestamo.isEmpty()){ throw new PrestamoNotFoundException(id, matricula, null); }

        LocalDate antigua = prestamo.get().getFecha_devolucion();
        LocalDate hoy = LocalDate.now();
        
        if(antigua.isBefore(hoy)){ throw new FechaDevolucionException(hoy,antigua);}

        prestamo.get().setFecha_devolucion(antigua.plusWeeks(2));
        repositoryprestamos.save(prestamo.get());
        return prestamo.get();
    }


    //para pruebas borrar todos los usuarios, eliminar al acabar codgio
    public void deleteTodos(){
        repository.deleteAll();
    }
}