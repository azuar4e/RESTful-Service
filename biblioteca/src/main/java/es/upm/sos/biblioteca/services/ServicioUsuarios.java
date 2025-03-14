package es.upm.sos.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.upm.sos.biblioteca.models.Usuario;
import es.upm.sos.biblioteca.repository.LibrosRepository;
import es.upm.sos.biblioteca.repository.UsuariosRepository;
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
    public Optional<Usuario> getUsuario(String matricula){
        return repository.getUsuario(matricula).orElseThrow(() -> new UsuarioNotFoundException(matricula));
    }
    
    //Metodo para obtener todos los usuarios | optional se usa para valores posibles null
    public Optional<List<Usuario>> getUsuarios(){
        return repository.getUsuarios(pageable);
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
