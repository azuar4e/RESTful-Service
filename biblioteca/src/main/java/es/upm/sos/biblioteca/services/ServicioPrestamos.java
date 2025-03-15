package es.upm.sos.biblioteca.services;


import java.util.List;
import java.util.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.upm.sos.biblioteca.models.Prestamo;
import es.upm.sos.biblioteca.repository.PrestamosRepository;
import lombok.*;

@Service
@AllArgsConstructor
public class ServicioPrestamos{
    @Autowired
    
    private final PrestamosRepository repository;

  //  public Optional<List<Prestamo>> getPrestamosMatricula(int matricula){
  //      return Optional.of(repository.getPrestamosMatricula(matricula));
  //  }

    //public Optional<List<Prestamo>> getPrestamosMatriculayFecha(int matricula, String fecha){
      //  if (fecha == null) { return Optional.of(repository.getPrestamosMatricula(matricula)); } 
        //else { return Optional.of(repository.getPrestamosMatriculayFecha(matricula, fecha)); }
   // }

    public Prestamo getPrestamoId(int id) {
      Optional<Prestamo> prestamo = repository.findById(id);

      if (!prestamo.isPresent()) { throw new PrestamoNotFoundException(id, null, null); }

      return prestamo.get();
    }

    public List<Prestamo> getPrestamosMatricula(String matricula, int page, int size){
      Pageable paginable = PageRequest.of(page, size);
      Page<Prestamo> prestamos = repository.findByUsuarioMatricula(matricula,paginable);

      if (prestamos.isEmpty()) { throw new PrestamoNotFoundException(null, matricula, isbn); }

      return prestamos;
    }

    public Prestamo getPrestamoMatriculaIsbn(String matricula, String isbn){
      Prestamo prestamo = prestamoRepository.findByUsuarioMatriculaAndLibroIsbn(matricula, isbn);

      if (prestamo == null) { throw new PrestamoNotFoundContentException(matricula); }

      return prestamo;
    }

    public List<Prestamo> getPrestamosPorFechaPrestamo(String matricula, LocalDate fechaPrestamo) {
        return prestamoRepository.findByUsuarioMatriculaAndFechaPrestamo(matricula, fechaPrestamo);
    }

    public List<Prestamo> getPrestamosPorFechaDevolucion(String matricula, LocalDate fechaDevolucion) {
      return prestamoRepository.findByUsuarioMatriculaAndFechaDevolucion(matricula, fechaDevolucion);
    }

    public List<Prestamo> getPrestamosPorFechaDevolucionPorFechaPrestamos(String matricula, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
      return prestamoRepository.findByUsuarioMatriculaAndFechaPrestamoAndFechaDevolucion(matricula, fechaPrestamo, fechaDevolucion);
    }

    public void actualizarFechaDevolucion(String matricula, String isbn, LocalDate fechaDevolucion) {

      LocalDate fechaActual = LocalDate.now();
      LocalDate fechaDevolucionActual = prestamoRepository.findByMatriculaAndIsbn(matricula, isbn).getFechaDevolucion();

      if (fechaActual.isBefore(fechaDevolucionActual)) {
        throw new FechaDevolucionException(fechaActual, fechaDevolucionActual);
      }

      prestamoRepository.actualizarFechaDevolucion(matricula, isbn, fechaDevolucion);
    }
}