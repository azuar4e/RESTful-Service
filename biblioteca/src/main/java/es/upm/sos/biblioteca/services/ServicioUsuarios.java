package es.upm.sos.biblioteca.services;


import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoConflictException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.UsuarioConflictException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.UsuarioNotFoundException;
import es.upm.sos.biblioteca.models.Usuario;
import es.upm.sos.biblioteca.models.Prestamo;
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
        return repository.save(u);
    }

    public Prestamo postPrestamoUsuario(String matricula, Prestamo prestamo){
        
        Usuario user = repository.getUsuario(matricula);
        if(user==null){  throw new UsuarioNotFoundException(matricula);}
        if (repositoryprestamos.findByLibroIsbn(prestamo.getLibro().getIsbn())!=null){
            throw new PrestamoConflictException(prestamo.getId());
        }
        user.getPrestamos().add(prestamo);
        return prestamo;
    }

    //Metodo para obtener los datos de un usuario a partir de su matricula
    public Usuario getUsuario(String matricula){
        Usuario user = repository.getUsuario(matricula);
        if(user==null){ throw new UsuarioNotFoundException(matricula);  }
        return user;
    }

    
    //Metodo para obtener todos los usuarios | optional se usa para valores posibles null
    public Page<Usuario> getUsuarios(int page, int size){
        Pageable paginable = PageRequest.of(page,size);
        return repository.getUsuarios(paginable);
    }

    //put
    public Usuario actualizarUsuario(String matricula, Usuario usuarioNuevo) {
        Optional<Usuario> usuarioExistente = Optional.of(repository.findByMatricula(matricula));
        if (!usuarioExistente.isPresent()) { throw new UsuarioNotFoundException(matricula);  }
        else{
            Usuario usuario = usuarioExistente.get();
            usuario.setNombre(usuarioNuevo.getNombre());
            usuario.setCorreo(usuarioNuevo.getCorreo());
            usuario.setFechaNacimiento(usuarioNuevo.getFechaNacimiento());
            usuario.setSancion(usuarioNuevo.getSancion());

            return repository.save(usuario);
        }
    }

    //metodo delete
    @Transactional
    public void deleteUsuario(String matricula){
        Optional<Usuario> usuarioExistente = Optional.of(repository.getUsuario(matricula)); 
        if(!usuarioExistente.isPresent()){  throw new UsuarioNotFoundException(matricula); }
        
        repository.deleteByMatricula(matricula);;
    }

    @Transactional//mirarlo
    public void deletePrestamo(String matricula, Integer id){
        Usuario user = repository.getUsuario(matricula);
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


    //para pruebas borrar todos los usuarios, eliminar al acabar codgio
    public void deleteTodos(){
        repository.deleteAll();
    }
}
