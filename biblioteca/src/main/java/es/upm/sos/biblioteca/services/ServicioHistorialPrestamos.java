package es.upm.sos.biblioteca.services;

import es.upm.sos.biblioteca.Excepciones.HistorialPrestamos.HistorialEntrePrestamosNull;
import es.upm.sos.biblioteca.Excepciones.HistorialPrestamos.HistorialIsNull;
import es.upm.sos.biblioteca.Excepciones.HistorialPrestamos.HistorialNotFound;
import es.upm.sos.biblioteca.models.HistorialPrestamos;
import es.upm.sos.biblioteca.repository.HistorialPrestamosRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor

public class ServicioHistorialPrestamos{
    @Autowired
    //repositorio al que llamamos para realizar las querys
    private final HistorialPrestamosRepository repository;

    public Page<HistorialPrestamos> getHistorialPrestamosPorMatricula(String matricula, int page, int size){
        Pageable paginable = PageRequest.of(page, size);
        Page<HistorialPrestamos> historial = repository.findByUsuarioMatricula(matricula, paginable);

        if(historial.isEmpty()){
            throw new HistorialNotFound(matricula);
        }

        return historial;
    }

    //Devuelve los prestamos de x fecha de devolucion
    public List<HistorialPrestamos> getPrestamosPorFechaDevolucion(String matricula, LocalDate fechaDevolucion) {
        List<HistorialPrestamos> historial = repository.findByUsuarioMatriculaAndFechaDevolucion(matricula, fechaDevolucion);

        if(historial.isEmpty()){
            throw new HistorialIsNull(matricula, fechaDevolucion);
        }
        return historial;
    }

    //Devuelve los prestamos en x fecha
    public List<HistorialPrestamos> getPrestamosPorFechaPrestamo(String matricula, LocalDate fechaPrestamo) {
        List<HistorialPrestamos> historial = repository.findByUsuarioMatriculaAndFechaPrestamo(matricula,fechaPrestamo);

        if(historial.isEmpty()){
            throw new HistorialIsNull(matricula,fechaPrestamo);
        }
        return historial;
    }

    public List<HistorialPrestamos> getPrestamosPorFechas(String matricula, LocalDate fechaPrestamos, LocalDate fechaDevolucion){
        List<HistorialPrestamos> historial = repository.findByUsuarioMatriculaAndFechaPrestamoAndFechaDevolucion(matricula,fechaPrestamos,fechaDevolucion);

        if(historial.isEmpty()) { throw new HistorialEntrePrestamosNull(matricula,fechaPrestamos,fechaDevolucion); }
        return historial;
    }
    
    //Metodo para obtener los ultimos cinco libros
    public Page<HistorialPrestamos> getUltimosCincoLibrosDevueltos(String matricula) {
        Pageable paginable = PageRequest.of(0, 5);
        Page<HistorialPrestamos> prestamos = repository.findByUsuarioMatricula(matricula,paginable);
  
        if (prestamos.isEmpty()) { throw new HistorialNotFound(matricula); }
  
        return prestamos;
    }

    //Metodo para devolver libros devueltos
    public Page<HistorialPrestamos> getUltimosLibrosDevueltos(String matricula, int page, int size) {
    Pageable paginable = PageRequest.of(page, size);
    Page<HistorialPrestamos> prestamos = repository.findByUsuarioMatricula(matricula,paginable);
    
    if (prestamos.isEmpty()) { throw new HistorialNotFound(matricula); }

    return prestamos;
    }

}