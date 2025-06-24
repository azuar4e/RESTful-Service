package es.upm.sos.biblioteca.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import es.upm.sos.biblioteca.controllers.UsuariosController;

@Component

public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario,Usuario> {
    public UsuarioModelAssembler() {
        super(UsuariosController.class, Usuario.class);
        }
    @Override
    public Usuario toModel(Usuario entity) {    //añadimos propia referencia
        // entity.add(linkTo(methodOn(UsuariosController.class).getUsuario(entity.getMatricula())).withSelfRel());
        return entity;
        }
}
