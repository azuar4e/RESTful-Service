package es.upm.sos.biblioteca.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import es.upm.sos.biblioteca.controllers.PrestamosController;

@Component

//sirve para añadir links de referencia 
public class PrestamoModelAssembler extends RepresentationModelAssemblerSupport<Prestamo,Prestamo> {
    public PrestamoModelAssembler() {
        super(PrestamosController.class, Prestamo.class);
        }
        //poner links para los diferentes metodos
        @Override
        public Prestamo toModel(Prestamo entity) {    //añadimos propia referencia
            entity.add(linkTo(methodOn(PrestamosController.class).getPrestamoId(entity.getId())).withSelfRel());

            return entity;
        }
}
