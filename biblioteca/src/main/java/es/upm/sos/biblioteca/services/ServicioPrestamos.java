package es.upm.sos.biblioteca.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.upm.sos.biblioteca.models.Libro;
import es.upm.sos.biblioteca.models.Prestamo;
import es.upm.sos.biblioteca.models.Usuario;
import es.upm.sos.biblioteca.repository.LibrosRepository;
import es.upm.sos.biblioteca.repository.PrestamosRepository;
import es.upm.sos.biblioteca.repository.UsuariosRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import lombok.*;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoVerificadoException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundContentException;
import es.upm.sos.biblioteca.Excepciones.Libros.LibroNotFoundException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.FechaDevolucionException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoConflictException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.LibroNoDisponibleException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.UsuarioDevolucionesPendientesException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.UsuarioSancionadoException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class ServicioPrestamos{    
    private final PrestamosRepository repository;
    private final LibrosRepository repoLibro;
    private static final Logger logger = LoggerFactory.getLogger(ServicioPrestamos.class);

    @Autowired
    private UsuariosRepository userrepo;

    public Page<Prestamo> getPrestamos(int page, int size){
    Pageable paginable = PageRequest.of(page, size);
      return repository.findAll(paginable);
    }

    public Prestamo getPrestamoId(int id) {
      Optional<Prestamo> prestamo = repository.findById(id);

      if (!prestamo.isPresent()) { throw new PrestamoNotFoundException(id, null, null); }

      return prestamo.get();
    }

    public Page<Prestamo> getPrestamosMatricula(String matricula, int page, int size){
      Pageable paginable = PageRequest.of(page, size);
      Page<Prestamo> prestamos = repository.findByUsuarioMatricula(matricula,paginable);

      if (prestamos.isEmpty()) { throw new PrestamoNotFoundException(null, matricula, null); }

      return prestamos;
    }

    public Prestamo getPrestamoMatriculaIsbn(String matricula, String isbn){
      Prestamo prestamo = repository.findByUsuarioMatriculaAndLibroIsbn(matricula, isbn);

      if (prestamo == null) { throw new PrestamoNotFoundException(null, matricula, isbn); }

      return prestamo;
    }

    public Page<Prestamo> getPrestamosPorFechaPrestamo(String matricula, LocalDate fechaPrestamo, int page, int size) {
      Pageable paginable = PageRequest.of(page, size);
      Page<Prestamo> prestamos = repository.findByUsuarioMatriculaAndFechaPrestamo(matricula, fechaPrestamo, paginable);
      if (prestamos == null) { throw new PrestamoNotFoundContentException(matricula, fechaPrestamo, null); }
      return prestamos;
    }

    public Page<Prestamo> getPrestamosPorFechaDevolucion(String matricula, LocalDate fechaDevolucion, int page, int size) {
      Pageable paginable = PageRequest.of(page, size);
      Page<Prestamo> prestamos = repository.findByUsuarioMatriculaAndFechaDevolucion(matricula, fechaDevolucion, paginable);
      if (prestamos == null) { throw new PrestamoNotFoundContentException(matricula, null, fechaDevolucion); }
      return prestamos;
    }

    public Page<Prestamo> getPrestamosPorFechaDevolucionPorFechaPrestamos(String matricula, LocalDate fechaPrestamo, LocalDate fechaDevolucion, int page, int size) {
      Pageable paginable = PageRequest.of(page, size);
      Page<Prestamo> prestamos = repository.findByUsuarioMatriculaAndFechaPrestamoAndFechaDevolucion(matricula, fechaPrestamo, fechaDevolucion, paginable);
      if (prestamos == null) { throw new PrestamoNotFoundContentException(matricula, fechaPrestamo, fechaDevolucion); }
      return prestamos;
    }

    @Transactional
    public void actualizarFechaDevolucion(int id, LocalDate fechaDevolucion) {

      Optional<Prestamo> prestamo = repository.findById(id);
      if (!prestamo.isPresent()) { throw new PrestamoNotFoundException((Integer) id, null, null); }

      LocalDate fechaActual = LocalDate.now();
      LocalDate fechaDevolucionActual = repository.findById(id).get().getFechaDevolucion();

      if (fechaActual.isBefore(fechaDevolucionActual)) {
        throw new FechaDevolucionException(fechaActual, fechaDevolucionActual);
      }

      prestamo.get().setFechaDevolucion(fechaDevolucion);
      repository.save(prestamo.get());
    }

    @Transactional
    public void postPrestamo(Prestamo prestamo) {
      Libro libro = repoLibro.findByIsbn(prestamo.getLibro().getIsbn());
      int cantidad = libro.getDisponibles();
      logger.info("Servicio postPrestamo");
      Optional<Prestamo> prestamoExistente = repository.findById(prestamo.getId());
      logger.info("Cantidad: "+cantidad);
      if (prestamoExistente.isPresent()) { throw new PrestamoConflictException(prestamo.getId()); }
      if(cantidad == 0) { throw new LibroNoDisponibleException(libro.getIsbn()); }
      if (prestamo.getUsuario().getPorDevolver() != 0) { throw new UsuarioDevolucionesPendientesException(prestamo.getUsuario().getMatricula()); }
      if (prestamo.getUsuario().getSancion() != null) { throw new UsuarioSancionadoException(prestamo.getUsuario().getMatricula()); } 
      libro.setDisponibles(cantidad-1);
      logger.info("Cantidad: "+ libro.getDisponibles());
      repoLibro.save(libro);
      repository.save(prestamo);
    }

    @Transactional
    public void devolverLibro(int id) {

      Optional<Prestamo> prestamo = repository.findById(id);
      if (!prestamo.isPresent()) { throw new PrestamoNotFoundException(id, null, null); }

      if (prestamo.get().getFechaDevolucion().isBefore(LocalDate.now())) {
        if (!prestamo.get().isVerificarDevolucion()) { verificarDevolucion(id); }
        Usuario user = userrepo.getUsuario(prestamo.get().getUsuario().getMatricula());
        user.setPorDevolver(user.getPorDevolver() - 1);

        if (user.getPorDevolver() == 0) {
          LocalDate sancion = LocalDate.now().plusWeeks(1);
          user.setSancion(sancion);
        }
        userrepo.save(user);
      }
      prestamo.get().setDevuelto(true);
      repository.save(prestamo.get());
    }

    @Transactional
    public void verificarDevolucion(int id) {
      Optional<Prestamo> prestamo = repository.findById(id);
      if (!prestamo.isPresent()) { throw new PrestamoNotFoundException(id, null, null); }

      if (prestamo.get().getFechaDevolucion().isBefore(LocalDate.now()) 
        && !prestamo.get().isDevuelto() && !prestamo.get().isVerificarDevolucion()) {

        prestamo.get().setVerificarDevolucion(true);
        Usuario user = userrepo.getUsuario(prestamo.get().getUsuario().getMatricula());
        user.setPorDevolver(user.getPorDevolver() + 1);
        userrepo.save(user);
        repository.save(prestamo.get());
      } else { throw new PrestamoVerificadoException(id); }
    }


    @Transactional
    public void deletePrestamo(int id) {
      Optional<Prestamo> prestamo = repository.findById(id);
      if (!prestamo.isPresent()) { throw new PrestamoNotFoundException(id, null, null); }
      Usuario usuario = prestamo.get().getUsuario();
      if (usuario != null) {
        usuario.getPrestamos().remove(prestamo.get()); 
        userrepo.save(usuario);
      }
      prestamo.get().getLibro().setDisponibles(prestamo.get().getLibro().getDisponibles()+1);
      repoLibro.save(prestamo.get().getLibro());
      repository.deleteById(id);
    }
}