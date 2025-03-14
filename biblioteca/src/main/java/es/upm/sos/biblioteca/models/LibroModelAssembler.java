package es.upm.sos.biblioteca.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import es.upm.sos.biblioteca.controllers.LibrosController;

@Component

//sirve para añadir links de referencia 
public class LibroModelAssembler extends RepresentationModelAssemblerSupport<Libro,Libro> {
    public LibroModelAssembler() {
        super(LibrosController.class, Libro.class);
        }
        @Override
        public Libro toModel(Libro entity) {    //añadimos propia referencia
        entity.add(linkTo(methodOn(LibrosController.class).getLibroIsbn(entity.getIsbn())).withSelfRel());
        return entity;
        }
}
