package es.upm.sos.biblioteca.services;

import es.upm.sos.biblioteca.models.HistorialPrestamos;
import es.upm.sos.biblioteca.repository.HistorialPrestamosRepository;
import es.upm.sos.biblioteca.repository.UsuariosRepository;
import lombok.*;
import java.util.LocalDate;

@Service
@AllArgsConstructor

public class ServicioHistorialPrestamos{
    @Autowired

    //repositorio al que llamamos para realizar las querys
    private final HistorialPrestamosRepository repository;

    public List<HistorialPrestamos> getHistorialPrestamosPorMatricula(String matricula){
        List<HistorialPrestamos> historial = repository.findByMatricula();

        if(historial.isEmpty()){
            throw new HistorialNotFound(matricula);
        }

        return historial;
    }

    /*
    public List<HistorialPrestamos>  getHistorialPorMatriculaYFecha(String matricula, Date fechaDevolucion){

    }
    */

    //Metodo para obtener los ultimos cinco libros
    public List<Prestamo> getUltimosCincoLibrosDevueltos(String matricula) {
        Pageable paginable = PageRequest.of(0, 5);
        Page<Prestamo> prestamos = repository.findByMatricula(matricula,paginable);
  
        if (prestamos == null) { throw new PrestamoNotFoundContentException(matricula); }
  
        return prestamos;
    }

    //Metodo para devolver libros devueltos
    public List<Prestamo> getUltimosLibrosDevueltos(String matricula, int page, int size) {
    Pageable paginable = PageRequest.of(page, size);
    Page<Prestamo> prestamos = repository.findByMatricula(matricula,paginable);
    
    if (prestamos == null) { throw new PrestamoNotFoundContentException(matricula); }

    return prestamos;
    }

}