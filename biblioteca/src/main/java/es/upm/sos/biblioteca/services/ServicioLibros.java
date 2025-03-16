package es.upm.sos.biblioteca.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.upm.sos.biblioteca.Excepciones.Usuarios.UsuarioNotFoundException;
import es.upm.sos.biblioteca.models.Usuario;
import es.upm.sos.biblioteca.repository.UsuariosRepository;
import jakarta.transaction.Transactional;
import lombok.*;

@Service //Marcamos la clase como componente de servicio
@AllArgsConstructor

public class ServicioUsuarios{
    @Autowired

    //repositorio al que llamamos para realizar las querys
    private final UsuariosRepository repository;

     //metodo para actualizar/guardar un usuario en la bbdd
    public Usuario postUsuario(Usuario u){
        return repository.save(u);
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

    //metodo delete
    @Transactional
    public void deleteUsuario(String matricula){
        if(repository.findByMatricula(matricula) == null){
            throw new UsuarioNotFoundException(matricula);
        }
        repository.deleteUsuario(matricula);
    }

    //para pruebas borrar todos los usuarios, eliminar al acabar codgio
    public void deleteTodos(){
        repository.deleteAll();
    }
}
