package es.upm.sos.biblioteca.services;

import es.upm.sos.biblioteca.models.HistorialPrestamos;
import es.upm.sos.biblioteca.repository.HistorialPrestamosRepository;
import es.upm.sos.biblioteca.repository.UsuariosRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.web.bind.annotation.*;
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

    public List<HistorialPrestamos> getHistorialPrestamosPorMatricula(String matricula){
        List<HistorialPrestamos> historial = repository.findByMatricula(matricula);

        if(historial.isEmpty()){
            throw new HistorialNotFound(matricula);
        }

        return historial;
    }

    //Devuelve los prestamos de x fecha de devolucion
    public List<HistorialPrestamos> getPrestamosPorFechaDevolucion(String matricula, LocalDate fechaDevolucion) {
        Pageable paginable = PageRequest.of(page,size);
        Page<HistorialPrestamos> historial = repository.findByUsuarioMatriculaAndFechaDevolucion(matricula,fechaPrestamo);

        if(historial.isEmpty()){
            throw new HistorialIsNull(matricula,fechaPrestamo);
        }
        return historial;
    }

    //Devuelve los prestamos en x fecha
    public List<HistorialPrestamos> getPrestamosPorFechaPrestamo(String matricula, LocalDate fechaPrestamo) {
        Pageable paginable = PageRequest.of(page,size);
        Page<HistorialPrestamos> historial = repository.findByUsuarioMatriculaAndFechaPrestamo(matricula,fechaPrestamo);

        if(historial.isEmpty()){
            throw new HistorialIsNull(matricula,fechaPrestamo);
        }
        return historial;
    }

    public List<HistorialPrestamos> getPrestamosPorFechas(String matricula, LocalDate fechaPrestamos, LocalDate fechaDevolucion){
        Pageable paginable = PageRequest.of(page,size);
        Page<HistorialPrestamos> historial = repository.findByUsuarioMatriculaAndFechaPrestamoAndFechaDevolucion(matricula,fechaPrestamos,fechaDevolucion);

        if(historial.isEmpty())
                throw new HistorialEntrePrestamosNull(matricula,fechaPrestamos,fechaDevolucion);

            return historial;
    }
    
    //Metodo para obtener los ultimos cinco libros
    public List<HistorialPrestamos> getUltimosCincoLibrosDevueltos(String matricula) {
        Pageable paginable = PageRequest.of(0, 5);
        Page<HistorialPrestamos> prestamos = repository.findByMatricula(matricula,paginable);
  
        if (prestamos.isEmpty()) { throw new HistorialIsEmpty(matricula); }
  
        return prestamos;
    }

    //Metodo para devolver libros devueltos
    public List<HistorialPrestamos> getUltimosLibrosDevueltos(String matricula, int page, int size) {
    Pageable paginable = PageRequest.of(page, size);
    Page<HistorialPrestamos> prestamos = repository.findByMatricula(matricula,paginable);
    
    if (prestamos.isEmpty()) { throw new HistorialIsEmpty(matricula); }

    return prestamos;
    }

}