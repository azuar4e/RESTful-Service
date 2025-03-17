package es.upm.sos.biblioteca.services;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.upm.sos.biblioteca.models.Prestamo;
import es.upm.sos.biblioteca.repository.PrestamosRepository;
import org.springframework.data.domain.*;
import lombok.*;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundContentException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.FechaDevolucionException;
import es.upm.sos.biblioteca.repository.PrestamosRepository;
import es.upm.sos.biblioteca.Excepciones.Libros.LibroNotFoundContentException;


@Service
@AllArgsConstructor
public class ServicioPrestamos{
    @Autowired
    
    private PrestamosRepository repository;

  //  public Optional<List<Prestamo>> getPrestamosMatricula(int matricula){
  //      return Optional.of(repository.getPrestamosMatricula(matricula));
  //  }

    //public Optional<List<Prestamo>> getPrestamosMatriculayFecha(int matricula, String fecha){
      //  if (fecha == null) { return Optional.of(repository.getPrestamosMatricula(matricula)); } 
        //else { return Optional.of(repository.getPrestamosMatriculayFecha(matricula, fecha)); }
   // }

    public Prestamo getPrestamoId(int id) {
      Optional<Prestamo> prestamo = repository.findById(id);

      if (!prestamo.isPresent()) { throw new PrestamoNotFoundException((Integer) id, null, null); }

      return prestamo.get();
    }

    public Page<Prestamo> getPrestamosMatricula(String matricula, int page, int size){
      Pageable paginable = PageRequest.of(page, size);
      Page<Prestamo> prestamos = repository.findByUsuarioMatricula(matricula,paginable);

      if (prestamos.isEmpty()) { throw new PrestamoNotFoundException(null, matricula, isbn); }

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

    public void actualizarFechaDevolucion(int id, LocalDate fechaDevolucion) {

      Optional<Prestamo> prestamo = repository.findById(id);
      if (!prestamo.isPresent()) { throw new PrestamoNotFoundException((Integer) id, null, null); }

      LocalDate fechaActual = LocalDate.now();
      LocalDate fechaDevolucionActual = repository.findById(id).getFechaDevolucion();

      if (fechaActual.isBefore(fechaDevolucionActual)) {
        throw new FechaDevolucionException(fechaActual, fechaDevolucionActual);
      }

      prestamo.get().setFechaDevolucion(fechaDevolucion);
      repository.save(prestamo.get());
    }

    public void deletePrestamo(int id) {
      Optional<Prestamo> prestamo = repository.findById(id);
      if (!prestamo.isPresent()) { throw new PrestamoNotFoundException((Integer) id, null, null); }
      repository.deleteById(id);
    }

    public void postPrestamo(Prestamo prestamo) {
      Optional<Prestamo> prestamoExistente = repository.findById(prestamo.getId());
      if (prestamoExistente.isPresent()) { throw new PrestamoConflictException(prestamo.getId()); }
      repository.save(prestamo);
    }

}