package es.upm.sos.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.upm.sos.biblioteca.models.Usuario;
import es.upm.sos.biblioteca.repository.LibrosRepository;
import lombok.*;

@Service //Marcamos la clase como componente de servicio
@AllArgsConstructor

public class ServicioUsuarios{
    @Autowired

    //repositorio al que llamamos para realizar las querys
    private final LibrosRepository repository;

}
