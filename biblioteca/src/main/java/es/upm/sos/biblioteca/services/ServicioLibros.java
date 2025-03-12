package es.upm.sos.biblioteca.services;

@Service //Marcamos la clase como componente de servicio
@AllArgsConstructor
public class ServicioLibros{

    //repositorio al que llamamos para realizar las querys
    private final LibrosRepository repository;

    //metodo para sacar de la base de datos los libros
    public Optional<List<Libros>> getLibros(){
        return repository.getLibros();
    }

    //metodo para sacar de la base de datos libros con contenido del titulo "contenido"
    public Optional<List<Libros>> getLibrosContenido(String contenido){
        return repository.getLibrosContenido(contenido);
    }

    //metodo para sacar de la base de datos libros disponibles
    //falta por hacer

    //metodo para sacar de la base de datos un libro a partir de su isbn
    public Optional<Libros> getLibroIsbn(String isbn){
        return repository.getLibroIsbn(isbn);
    }
    
    //metodo para guardar un libro en la tabla de libros/actualizar
    public Libro postLibro(Libro libro){
        return repository.save(libro);
    }

    public void actualizarLibro(Libro libro){
        repository.actualizarLibroPrestamo(libro.getPrestamo(), libro.getId());
    }

//metodo delete
    public void deleteLibro(String Isbn){
        repository.deleteLibro(isbn);
    }
}
