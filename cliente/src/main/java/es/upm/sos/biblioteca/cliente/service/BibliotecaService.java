package es.upm.sos.biblioteca.cliente.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.MediaType;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.hateoas.PagedModel; 

import org.springframework.http.HttpStatusCode;

import es.upm.sos.biblioteca.cliente.models.Libro;
import es.upm.sos.biblioteca.cliente.models.Prestamo;
import es.upm.sos.biblioteca.cliente.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.data.domain.Page;




public class BibliotecaService {

    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/biblioteca.api").build();

    public void getUsuario(String matricula){
        Usuario user = webClient.get().uri("/users/{matricula}",matricula).retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        )
        .bodyToMono(Usuario.class)
        .block();

        if(user != null){
            String selfLink = user.get_links().getFirstHref();
            System.out.println("Usuario con matricula: "+user.getMatricula()+ 
            " y correo: "+user.getCorreo() + " se encuentra disponible en el link: "+ selfLink);
        }
    }

    // Obtener todos los usuarios paginados
    public void getUsuarios(int page, int size) {
        Usuario usuarios = webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/users")
                .queryParam("page", page)
                .queryParam("size", size)
                .build())
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 4xx: " + body))
                .then(Mono.empty()))
            .onStatus(HttpStatusCode::is5xxServerError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 5xx: " + body))
                .then(Mono.empty()))
            .bodyToMono(Usuario.class)
            .block();

        if (usuarios != null) {
            String selfLink = usuarios.get_links().getFirstHref();
            System.out.println("Los usuarios se encuentran disponibles en el link: " + selfLink);
        }
    }
    

    //Get de libro
    public void getLibro(String isbn){
        Libro libro = webClient.get().uri("/libros/{isbn}",isbn).retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        )
        .bodyToMono(Libro.class)
        .block();

        if(libro != null){
            String selfLink = libro.get_links().getFirstHref();
            System.out.println("Libro con isbn: "+libro.getIsbn()+ 
            " y nombre: "+ libro.getTitulo() + " se encuentra disponible en el link: "+ selfLink);
        }
    }

    public void getLibros(int page, int size) {
        Libro libros = webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/prestamos")
                .queryParam("page", page)
                .queryParam("size", size)
                .build())
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 4xx: " + body))
                .then(Mono.empty()))
            .onStatus(HttpStatusCode::is5xxServerError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 5xx: " + body))
                .then(Mono.empty()))
            .bodyToMono(Libro.class)
            .block();

        if (libros != null) {
            String selfLink = libros.get_links().getFirstHref();
            System.out.println("Los libros se encuentran disponibles en el link: " + selfLink);
        }
    }
    
    public void getPrestamo(int id){
        Prestamo prestamo = webClient.get().uri("/prestamos/{id}",id).retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        )
        .bodyToMono(Prestamo.class)
        .block();

        if(prestamo != null){
            String selfLink = prestamo.get_links().getFirstHref();
            System.out.println("prestamo con id: "+prestamo.getId()+ 
            " se encuentra disponible en el link: " + selfLink);
        }
    }

    public void getPrestamos(int page, int size) {
        Prestamo prestamos = webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/prestamos")
                .queryParam("page", page)
                .queryParam("size", size)
                .build())
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 4xx: " + body))
                .then(Mono.empty()))
            .onStatus(HttpStatusCode::is5xxServerError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 5xx: " + body))
                .then(Mono.empty()))
            .bodyToMono(Prestamo.class)
            .block();

        if (prestamos != null) {
            String selfLink = prestamos.get_links().getFirstHref();
            System.out.println("Los prestamos se encuentran disponibles en el link: " + selfLink);
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
        prestamo.setFecha_prestamo(fechaPrestamo);
        prestamo.setFecha_devolucion(fechaDevolucion);
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
        prestamo.setFecha_prestamo(fechaPrestamo);
        prestamo.setFecha_devolucion(fechaDevolucion);
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

    public void getPrestamosUsuario(String matricula, LocalDate fechaPrestamo, LocalDate fechaDevolucion, boolean devuelto, int page, int size) {
    // StringBuilder uri = new StringBuilder("/usuarios/{matricula}/prestamos?page=" + page + "&size=" + size);

    Prestamo prestamo = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/users/{matricula}/prestamos")
            .queryParam("devuelto", devuelto)
            .queryParam("fecha_prestamo", fechaPrestamo)
            .queryParam("fecha_devolucion", fechaDevolucion)
            .queryParam("page", page)
            .queryParam("size", size)
            .build(matricula))
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response ->
            response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 4xx: " + body))
                .then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError, response ->
            response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 5xx: " + body))
                .then(Mono.empty())
        )
        .bodyToMono(Prestamo.class)
        .block();

       if (prestamo != null) {
        String selfLink = prestamo.get_links().getFirstHref();
        System.out.println("Los prestamos se encuentran en: "+selfLink);
    }
}

    public void getPorTitulo(String titulo){
            Libro libro = webClient.get()
            .uri("/libros?tituloContiene={titulo}", titulo)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
            .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
            )
            .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
            .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
            )
            .bodyToMono(Libro.class)
            .block();

        if (libro != null) {
            String selfLink = libro.get_links().getFirstHref();
            System.out.println("Los libros que contienen \'" + titulo + 
            "\' en el titulo se encuentran disponibles en el link: " + selfLink);
        }
    }

    public void putActualizarDevolucion(int id, LocalDate fechaDevolucion){
        webClient.put().uri("/prestamos/{id}/actualizar-devolucion?fechaDevolucion={fechaDevolucion}",id, fechaDevolucion)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 4xx: " + body))
                .then(Mono.empty()))
            .onStatus(HttpStatusCode::is5xxServerError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 5xx: " + body))
                .then(Mono.empty()))
            .toBodilessEntity()
            .block();
    }

    
    public void putPrestamoDevolverLibro(int id) {
        webClient.put()
            .uri("/prestamos/{id}/devolucion", id)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 4xx: " + body))
                .then(Mono.empty()))
            .onStatus(HttpStatusCode::is5xxServerError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 5xx: " + body))
                .then(Mono.empty()))
            .toBodilessEntity()
            .block();
    }
    
    public void putVerificarDevolucion(int id) {
        webClient.put()
            .uri("/prestamos/{id}/verificar-devolucion", id)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 4xx: " + body))
                .then(Mono.empty()))
            .onStatus(HttpStatusCode::is5xxServerError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 5xx: " + body))
                .then(Mono.empty()))
            .toBodilessEntity()
            .block();
    }

    public void getActividadUsuarioLink(String matricula) {
        try {
            Map<String, Object> response = webClient.get()
                .uri("/users/{matricula}/actividad", matricula)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

            if (response != null && response.containsKey("_links")) {
                Map<String, Object> links = (Map<String, Object>) response.get("_links");
                Map<String, String> selfLink = (Map<String, String>) links.get("self");
                String href = selfLink.get("href");
                
                System.out.println("Link de actividad del usuario: " + href);
            } else {
                System.out.println("No se encontró link en la respuesta.");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener actividad: " + e.getMessage());
        }
    }

       //Get de libro
    public Integer getLibroUnidades(String isbn){
        return webClient.get().uri("/libros/{isbn}/unidades",isbn).retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 4xx: " + body)).then(Mono.empty())
        )
        .onStatus(HttpStatusCode::is5xxServerError,response -> response.bodyToMono(String.class)
        .doOnNext(body -> System.err.println("Error 5xx"+body)).then(Mono.empty())
        )
        .bodyToMono(Integer.class)
        .block();

    }

    public Prestamo ampliarPrestamo(String matricula, int idPrestamo) {
        Prestamo prestamo = webClient.put()
            .uri("/users/{matricula}/prestamos/{id}/ampliar", matricula, idPrestamo)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 4xx: " + body))
                .then(Mono.empty()))
            .onStatus(HttpStatusCode::is5xxServerError, response -> response.bodyToMono(String.class)
                .doOnNext(body -> System.err.println("Error 5xx: " + body))
                .then(Mono.empty()))
            .bodyToMono(Prestamo.class)
            .block();

        System.out.println("Préstamo ampliado con éxito.");
        return prestamo;
    }
    
}