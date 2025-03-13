package es.upm.sos.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.upm.sos.biblioteca.models.Libro;
import es.upm.sos.biblioteca.repository.LibrosRepository;
import lombok.*;

@Service //Marcamos la clase como componente de servicio
@AllArgsConstructor

public class ServicioLibros{
    @Autowired

    //repositorio al que llamamos para realizar las querys
    private final LibrosRepository repository;

    //metodo para sacar de la base de datos los libros
    public Optional<List<Libro>> getLibros(){
        return Optional.of(repository.getLibros());
    }

    //metodo para sacar de la base de datos libros con contenido del titulo "contenido"
    public Optional<List<Libro>> getLibrosContenido(String contenido){
        return Optional.of(repository.getLibrosContenido(contenido));
    }

    //metodo para sacar de la base de datos libros disponibles
    //falta por hacer

    //metodo para sacar de la base de datos un libro a partir de su isbn
    public Optional<Libro> getLibroIsbn(String isbn){
        return Optional.of(repository.getLibroIsbn(isbn));
    }
    
    //metodo para guardar un libro en la tabla de libros/actualizar
    public Libro postLibro(Libro libro){
        return repository.save(libro);
    }

  //  public void actualizarLibro(Libro libro){
  //      repository.actualizarLibroPrestamo(libro.getPrestamo(), libro.getId());
   // }

//metodo delete
    public void deleteLibro(String isbn){
        repository.deleteLibro(isbn);
    }
}
