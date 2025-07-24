package es.upm.sos.biblioteca.services;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import jakarta.transaction.Transactional;
import lombok.*;

import es.upm.sos.biblioteca.models.Libro;
import es.upm.sos.biblioteca.models.Prestamo;
import es.upm.sos.biblioteca.models.Usuario;
import es.upm.sos.biblioteca.repository.LibrosRepository;
import es.upm.sos.biblioteca.repository.PrestamosRepository;
import es.upm.sos.biblioteca.repository.UsuariosRepository;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoVerificadoException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundContentException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.FechasNoValidasException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoConflictException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.LibroNoDisponibleException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.UsuarioDevolucionesPendientesException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.UsuarioSancionadoException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoDevueltoException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.UsuarioNotFoundException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.LibroNoCoincideException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoFechaDevolucionNoCoincideException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoFechaPrestamoNoCoincideException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.UsuarioNoCoincideException;

@Service
@AllArgsConstructor
public class ServicioPrestamos{    
    private final PrestamosRepository repository;
    private final LibrosRepository repoLibro;
    private final UsuariosRepository userRepo;
    private static final Logger logger = LoggerFactory.getLogger(ServicioPrestamos.class);

    public Page<Prestamo> getPrestamos(int page, int size){
    Pageable paginable = PageRequest.of(page, size);
      return repository.findAll(paginable);
    }

    public Prestamo getPrestamoId(int id) {
      Optional<Prestamo> prestamo = repository.findById(id);

      if (prestamo.isEmpty()) { throw new PrestamoNotFoundException(id, null, null); }

      return prestamo.get();
    }

    public Page<Prestamo> getPrestamosActuales(String matricula, int page, int size) {
      Pageable paginable = PageRequest.of(page, size);
      Page<Prestamo> prestamos = repository.getPrestamosActuales(matricula, paginable);
      if (prestamos == null) { throw new PrestamoNotFoundContentException(matricula, null, null); }
      return prestamos;
    }

    public Page<Prestamo> getUltimosLibrosDevueltos(String matricula, int page, int size) {
      Usuario usuario = userRepo.findByMatricula(matricula);
      if (usuario == null) { throw new UsuarioNotFoundException(matricula); }
      
      Pageable paginable = PageRequest.of(page, size);
      Page<Prestamo> prestamos = repository.getUltimosLibrosDevueltos(matricula, paginable);
      if (prestamos == null) { throw new PrestamoNotFoundContentException(matricula, null, null); }
      return prestamos;
    }

    @Transactional
    public void postPrestamo(Prestamo prestamo) {
      Libro libro = repoLibro.findByIsbn(prestamo.getLibro().getIsbn());
      Usuario usuario = userRepo.getUsuario(prestamo.getUsuario().getMatricula());
      int cantidad = libro.getDisponibles();
      logger.info("Servicio postPrestamo");
      Optional<Prestamo> prestamoExistente = repository.findById(prestamo.getId());
      logger.info("Cantidad: "+cantidad);
      if (prestamoExistente.isPresent()) { throw new PrestamoConflictException(prestamo.getId()); }
      if(cantidad == 0) { throw new LibroNoDisponibleException(libro.getIsbn()); }
      if (usuario.getPor_devolver() != 0) { throw new UsuarioDevolucionesPendientesException(prestamo.getUsuario().getMatricula()); }
      if (usuario.getSancion() != null) { throw new UsuarioSancionadoException(prestamo.getUsuario().getMatricula()); } 
      if(prestamo.getFecha_devolucion().isBefore(prestamo.getFecha_prestamo())) { throw new FechasNoValidasException(prestamo.getFecha_prestamo(), prestamo.getFecha_devolucion()); }
      libro.setDisponibles(cantidad-1);
      logger.info("Cantidad: "+ libro.getDisponibles());
      repoLibro.save(libro);
      repository.save(prestamo);
    }

    @Transactional
    public void devolverLibro(int id, Prestamo pr) {

      Optional<Prestamo> prestamo = repository.findById(id);
      if (prestamo.isEmpty()) { throw new PrestamoNotFoundException(id, null, null); }
      if (prestamo.get().isDevuelto()) { throw new PrestamoDevueltoException(id); }

      if (!Objects.equals(pr.getFecha_prestamo(), prestamo.get().getFecha_prestamo())) {
          throw new PrestamoFechaPrestamoNoCoincideException(id, pr.getFecha_prestamo(), prestamo.get().getFecha_prestamo());
      }

      if (!Objects.equals(pr.getFecha_devolucion(), prestamo.get().getFecha_devolucion())) {
          throw new PrestamoFechaDevolucionNoCoincideException(id, pr.getFecha_devolucion(), prestamo.get().getFecha_devolucion());
      }

      if (!Objects.equals(pr.getUsuario().getMatricula(), prestamo.get().getUsuario().getMatricula())) {
          throw new UsuarioNoCoincideException(id, pr.getUsuario().getMatricula(), prestamo.get().getUsuario().getMatricula());
      }

      if (!Objects.equals(pr.getLibro().getIsbn(), prestamo.get().getLibro().getIsbn())) {
          throw new LibroNoCoincideException(id, pr.getLibro().getIsbn(), prestamo.get().getLibro().getIsbn());
      }

      if (prestamo.get().getFecha_devolucion().isBefore(LocalDate.now()) && !prestamo.get().isDevuelto()) {
        if (!prestamo.get().isVerificar_devolucion()) { verificarDevolucion(id, pr); }
        Usuario user = userRepo.getUsuario(prestamo.get().getUsuario().getMatricula());
        user.setPor_devolver(user.getPor_devolver() - 1);

        if (user.getPor_devolver() == 0) {
          LocalDate sancion = LocalDate.now().plusWeeks(1);
          user.setSancion(sancion);
        }
        
        userRepo.save(user);
      }

      Libro libro = prestamo.get().getLibro();
      libro.setDisponibles(libro.getDisponibles()+1);
      prestamo.get().setDevuelto(true);
      repoLibro.save(libro);
      repository.save(prestamo.get());
    }


    @Transactional
    public void verificarDevolucion(int id, Prestamo pr) {
      Optional<Prestamo> prestamo = repository.findById(id);
      if (prestamo.isEmpty()) { throw new PrestamoNotFoundException(id, null, null); }
      if (!Objects.equals(pr.getFecha_prestamo(), prestamo.get().getFecha_prestamo())) {
          throw new PrestamoFechaPrestamoNoCoincideException(id, pr.getFecha_prestamo(), prestamo.get().getFecha_prestamo());
      }

      if (!Objects.equals(pr.getFecha_devolucion(), prestamo.get().getFecha_devolucion())) {
          throw new PrestamoFechaDevolucionNoCoincideException(id, pr.getFecha_devolucion(), prestamo.get().getFecha_devolucion());
      }

      if (!Objects.equals(pr.getUsuario().getMatricula(), prestamo.get().getUsuario().getMatricula())) {
          throw new UsuarioNoCoincideException(id, pr.getUsuario().getMatricula(), prestamo.get().getUsuario().getMatricula());
      }

      if (!Objects.equals(pr.getLibro().getIsbn(), prestamo.get().getLibro().getIsbn())) {
          throw new LibroNoCoincideException(id, pr.getLibro().getIsbn(), prestamo.get().getLibro().getIsbn());
      }

      if (prestamo.get().getFecha_devolucion().isBefore(LocalDate.now()) 
        && !prestamo.get().isDevuelto() && !prestamo.get().isVerificar_devolucion()) {

        prestamo.get().setVerificar_devolucion(true);
        Usuario user = userRepo.getUsuario(prestamo.get().getUsuario().getMatricula());
        user.setPor_devolver(user.getPor_devolver() + 1);
        userRepo.save(user);
        repository.save(prestamo.get());
      } else { throw new PrestamoVerificadoException(id); }
    }


    @Transactional
    public void deletePrestamo(int id) {
      Optional<Prestamo> prestamo = repository.findById(id);
      if (prestamo.isEmpty()) { throw new PrestamoNotFoundException(id, null, null); }
      Usuario usuario = prestamo.get().getUsuario();
      if (usuario != null) {
        usuario.getPrestamos().remove(prestamo.get()); 
        userRepo.save(usuario);
      }
      prestamo.get().getLibro().setDisponibles(prestamo.get().getLibro().getDisponibles()+1);
      repoLibro.save(prestamo.get().getLibro());
      repository.deleteById(id);
    }
}