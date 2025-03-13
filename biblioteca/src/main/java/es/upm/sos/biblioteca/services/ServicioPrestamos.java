package es.upm.sos.biblioteca.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.upm.sos.biblioteca.models.Prestamo;
import es.upm.sos.biblioteca.repository.PrestamosRepository;
import lombok.*;

@Service
@AllArgsConstructor
public class ServicioPrestamos{
    @Autowired
    
    private final PrestamosRepository repository;

    public Optional<List<Prestamo>> getPrestamosMatricula(int matricula){
        return Optional.of(repository.getPrestamosMatricula(matricula));
    }

    public Optional<List<Prestamo>> getPrestamosMatriculayFecha(int matricula, String fecha){
        if (fecha == null) { return Optional.of(repository.getPrestamosMatricula(matricula)); } 
        else { return Optional.of(repository.getPrestamosMatriculayFecha(matricula, fecha)); }
    }


}