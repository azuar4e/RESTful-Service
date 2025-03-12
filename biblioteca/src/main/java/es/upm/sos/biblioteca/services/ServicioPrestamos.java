package es.upm.sos.biblioteca.services;

@Service
@AllArgsConstructor
public class ServicioLibros{

    private final PrestamosRepository repository;

    public Optional<List<Prestamos>> getPrestamosMatricula(int matricula){
        return repository.getPrestamosMatricula(matricula);
    }

    public Optional<List<Prestamos>> getPrestamosMatriculayFecha(int matricula, String fecha){
        if (fecha == null) { return ParametrosRepository.getPrestamosMatricula(matricula); } 
        else { return ParametrosRepository.getPrestamosMatriculayFecha(matricula, fecha); }
    }


}