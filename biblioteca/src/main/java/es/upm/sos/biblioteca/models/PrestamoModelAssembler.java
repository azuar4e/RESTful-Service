package es.upm.sos.biblioteca.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import es.upm.sos.biblioteca.controllers.PrestamosController;

@Component

//sirve para añadir links de referencia 
public class PrestamoModelAssembler extends RepresentationModelAssemblerSupport<Prestamo, Prestamo> {
    public PrestamoModelAssembler(){
        super(PrestamosController.class, Prestamo.class);
    }
    
    @Override
    public Prestamo toModel(Prestamo entity) {
        entity.add(linkTo(methodOn(PrestamosController.class).getPrestamo(entity.getId())).withSelfRel());
        return entity;
    }
}