package es.upm.sos.biblioteca.cliente.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.MediaType;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.http.HttpStatusCode;

import es.upm.sos.biblioteca.cliente.models.Libro;
import es.upm.sos.biblioteca.cliente.models.Prestamo;
import es.upm.sos.biblioteca.cliente.models.Usuario;
import reactor.core.publisher.Mono;

public class BibliotecaService {

    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/biblioteca.api").build();

    public void getUsuario(String matricula){
        Usuario user = webClient.get().uri("/users/",matricula).retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        )
        .bodyToMono(Usuario.class)
        .block();

        if(user != null){
            String selfLink = user.get_links().getSelf().getHref();
            System.out.println("Usuario con matricula: "+user.getMatricula()+ 
            " y correo: "+user.getCorreo() + " se eencuentra disponible en el link: "+ selfLink);
        }
    }

    //Get de libro
    public void getLibro(String isbn){
        Libro libro = webClient.get().uri("/libros/",isbn).retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        )
        .bodyToMono(Libro.class)
        .block();

        if(libro != null){
            String selfLink = libro.get_links().getSelf().getHref();
            System.out.println("Libro con isbn: "+libro.getIsbn()+ 
            " y nombre: "+ libro.getTitulo() + " se encuentra disponible en el link: "+ selfLink);
        }
    }
    
    public void getPrestamo(int id){
        Prestamo prestamo = webClient.get().uri("/prestamos/",id).retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        )
        .bodyToMono(Prestamo.class)
        .block();

        if(prestamo != null){
            String selfLink = prestamo.get_links().getSelf().getHref();
            System.out.println("prestamo con id: "+prestamo.getId()+ 
            " del usuario: "+ prestamo.getUsuario().getMatricula() + " y isbn: " + prestamo.getLibro().getIsbn() + " se encuentra disponible en el link: " + selfLink);
        }
    }

    

    public void postUsuario(String matricula, String nombre, String correo,
    String fechaNacimiento, LocalDate sancion, int porDevolver) {

        Usuario user = new Usuario();
        user.setMatricula(matricula);
        user.setNombre(nombre);
        user.setCorreo(correo);
        user.setFechaNacimiento(fechaNacimiento);
        user.setSancion(sancion);
        user.setPorDevolver(porDevolver);

        try {
            String referencia = webClient.post()
            .uri("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(user), Usuario.class)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 4xx: "+body))
                .then(Mono.empty()))
            .toBodilessEntity()
            .map(response -> {
                if (response.getHeaders().getLocation() != null){
                    return response.getHeaders().getLocation().toString();
                } else {
                    throw new RuntimeException("No se recibió URL en la cabecera Location");
                }
            })
            .block();

            if (referencia != null) {
                System.out.println(referencia);
            }
        } catch (RuntimeException e) {
            System.err.println("Error: "+e.getMessage());
        }
    }
    
    public void postPrestamo(Usuario user, Libro libro, LocalDate fechaPrestamo,
    LocalDate fechaDevolucion, boolean devuelto, boolean verificarDevolucion) {

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(user);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setDevuelto(devuelto);
        prestamo.setVerificarDevolucion(verificarDevolucion);

        try {
            String referencia = webClient.post()
            .uri("/prestamos")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(prestamo), Prestamo.class)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 4xx: "+body))
                .then(Mono.empty()))
            .toBodilessEntity()
            .map(response -> {
                if (response.getHeaders().getLocation() != null){
                    return response.getHeaders().getLocation().toString();
                } else {
                    throw new RuntimeException("No se recibió URL en la cabecera Location");
                }
            })
            .block();

            if (referencia != null) {
                System.out.println(referencia);
            }
        } catch (RuntimeException e) {
            System.err.println("Error: "+e.getMessage());
        }        
    }

    public void postLibro(String isbn, String titulo, String autores, String edicion, String editorial, int unidades, int disponibles) {
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutores(autores);
        libro.setEdicion(edicion);      
        libro.setEditorial(editorial);
        libro.setUnidades(unidades);
        libro.setDisponibles(disponibles);

        try {
            String referencia = webClient.post()
            .uri("/libros")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(libro), Libro.class)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 4xx: "+body))
                .then(Mono.empty()))
            .toBodilessEntity()
            .map(response -> {
                if (response.getHeaders().getLocation() != null){
                    return response.getHeaders().getLocation().toString();
                } else {
                    throw new RuntimeException("No se recibió URL en la cabecera Location");
                }
            })
            .block();

            if (referencia != null) {
                System.out.println(referencia);
            }
        } catch (RuntimeException e) {
            System.err.println("Error: "+e.getMessage());
        }        
    }

    public void putUsuario(String matricula, String nombre
    , String correo, String fechaNacimiento, LocalDate sancion, int porDevolver){
        Usuario user = new Usuario();
        user.setMatricula(matricula);
        user.setNombre(nombre);
        user.setCorreo(correo);
        user.setFechaNacimiento(fechaNacimiento);
        user.setSancion(sancion);
        user.setPorDevolver(porDevolver);
        webClient.put()
        .uri("/users/{matricula}", matricula)
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(user),Usuario.class)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        ).toBodilessEntity()
        .block();
    }
    
    public void putLibro(String isbn, String titulo
    , String autores, String edicion, String editorial, int disponibles, int unidades){
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutores(autores);
        libro.setEdicion(edicion);
        libro.setEditorial(editorial);
        libro.setDisponibles(disponibles);
        libro.setUnidades(unidades);
        webClient.put()
        .uri("/libros/{isbn}", isbn)
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(libro),Usuario.class)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        ).toBodilessEntity()
        .block();
    }

    public void putPrestamo(int id, Usuario user, Libro libro, LocalDate fechaPrestamo,
    LocalDate fechaDevolucion, boolean devuelto, boolean verificarDevolucion){
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(user);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setDevuelto(devuelto);
        prestamo.setVerificarDevolucion(verificarDevolucion);
        webClient.put()
        .uri("/prestamos/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(user),Usuario.class)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        ).toBodilessEntity()
        .block();
    }

    public void deleteLibro(String isbn) {
        webClient.delete()
        .uri("/libros/{isbn}", isbn)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body))
        .then(Mono.empty()))
        .toBodilessEntity()
        .block();        
    }

    public void deletePrestamo(int id) {
        webClient.delete()
        .uri("/prestamos/{id}", id)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body))
        .then(Mono.empty()))
        .toBodilessEntity()
        .block();        
    }

    public void deleteUsuario(String matricula) {
        webClient.delete()
        .uri("/users/{matricula}", matricula)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body))
        .then(Mono.empty()))
        .toBodilessEntity()
        .block();
    }

    //______________________________________________________________________________________________________________
    // metodos auxiliares

    public void getPrestamosUsuario(String matricula){
        Prestamo prestamo = webClient.get().uri("/prestamos?matricula={matricula}", matricula).retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        )
        .bodyToMono(Prestamo.class)
        .block();

        if (prestamo != null) {
            String selfLink = prestamo.get_links().getSelf().getHref();
            System.out.println("Prestamo con id: " + prestamo.getId() +
                " del usuario: " + prestamo.getUsuario().getMatricula() +
                " y isbn: " + prestamo.getLibro().getIsbn() +
                " se encuentra disponible en el link: " + selfLink);
        }
    }

    public void getUltimosLibrosDevueltos(String matricula){
        Prestamo prestamo = webClient.get().uri("/{matricula}/ultimos-libros-devueltos", matricula).retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        )
        .bodyToMono(Prestamo.class)
        .block();

        if (prestamo != null) {
            String selfLink = prestamo.get_links().getSelf().getHref();
            System.out.println("Prestamo con id: " + prestamo.getId() +
                " del usuario: " + prestamo.getUsuario().getMatricula() +
                " y isbn: " + prestamo.getLibro().getIsbn() +
                " se encuentra disponible en el link: " + selfLink);
        }
    }

    public void getPrestamosMatriculaIsbn(String matricula, String isbn){
        Prestamo prestamo = webClient.get().uri("/prestamos?matricula={matricula}&isbn={isbn}", matricula, isbn).retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        )
        .bodyToMono(Prestamo.class)
        .block();

        if (prestamo != null) {
            String selfLink = prestamo.get_links().getSelf().getHref();
            System.out.println("Prestamo con id: " + prestamo.getId() +
                " del usuario: " + prestamo.getUsuario().getMatricula() +
                " y isbn: " + prestamo.getLibro().getIsbn() +
                " se encuentra disponible en el link: " + selfLink);
        }
    }

    public void getPrestamosPorFechaPrestamo(String matricula, LocalDate fechaPrestamo){
        Prestamo prestamo = webClient.get().uri("/prestamos?matricula={matricula}&fechaPrestamo={fechaPrestamo}", matricula, fechaPrestamo).retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        )
        .bodyToMono(Prestamo.class)
        .block();

        if (prestamo != null) {
            String selfLink = prestamo.get_links().getSelf().getHref();
            System.out.println("Prestamo con id: " + prestamo.getId() +
                " del usuario: " + prestamo.getUsuario().getMatricula() +
                " y isbn: " + prestamo.getLibro().getIsbn() +
                " se encuentra disponible en el link: " + selfLink);
        }
    }

    public void getPrestamosPorFechaDevolucion(String matricula, LocalDate fechaDevolucion){
        Prestamo prestamo = webClient.get().uri("/prestamos?matricula={matricula}&fechaDevolucion={fechaDevolucion}", matricula, fechaDevolucion).retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        )
        .bodyToMono(Prestamo.class)
        .block();

        if (prestamo != null) {
            String selfLink = prestamo.get_links().getSelf().getHref();
            System.out.println("Prestamo con id: " + prestamo.getId() +
                " del usuario: " + prestamo.getUsuario().getMatricula() +
                " y isbn: " + prestamo.getLibro().getIsbn() +
                " se encuentra disponible en el link: " + selfLink);
        }
    }

    public void getPrestamosPorFechaDevolucionPorFechaPrestamos(String matricula, LocalDate fechaPrestamo, LocalDate fechaDevolucion){
        Prestamo prestamo = webClient.get().uri("/prestamos?matricula={matricula}&fechaPrestamo={fechaPrestamo}&fechaDevolucion={fechaDevolucion}", matricula, fechaPrestamo, fechaDevolucion).retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        )
        .bodyToMono(Prestamo.class)
        .block();

        if (prestamo != null) {
            String selfLink = prestamo.get_links().getSelf().getHref();
            System.out.println("Prestamo con id: " + prestamo.getId() +
                " del usuario: " + prestamo.getUsuario().getMatricula() +
                " y isbn: " + prestamo.getLibro().getIsbn() +
                " se encuentra disponible en el link: " + selfLink);
        }
    }


    public void putActualizarDevolucion(int id, LocalDate fechaDevolucion, Prestamo prestamo){
        Prestamo prestamo1 = webClient.get().uri("/prestamos/{id}/actualizar-devolucion",id).retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        )
        .bodyToMono(Prestamo.class)
        .block();

        if (prestamo != null) {
            String selfLink = prestamo.get_links().getSelf().getHref();
            System.out.println("Prestamo con id: " + prestamo.getId() +
                " del usuario: " + prestamo.getUsuario().getMatricula() +
                " y isbn: " + prestamo.getLibro().getIsbn() +
                " se encuentra disponible en el link: " + selfLink);
        }
    }

    public void putPrestamoDevolverLibro(int id, Usuario user, Libro libro, LocalDate fechaPrestamo,
    LocalDate fechaDevolucion, boolean devuelto, boolean verificarDevolucion){
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(user);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setDevuelto(devuelto);
        prestamo.setVerificarDevolucion(verificarDevolucion);
        webClient.put()
        .uri("/prestamos/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(user),Usuario.class)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        ).toBodilessEntity()
        .block();
    }    
}