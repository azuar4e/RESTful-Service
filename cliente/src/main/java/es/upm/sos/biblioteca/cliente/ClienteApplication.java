package es.upm.sos.biblioteca.cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import es.upm.sos.biblioteca.cliente.models.*;
import es.upm.sos.biblioteca.cliente.service.BibliotecaService;

@SpringBootApplication
public class ClienteApplication {
	static BibliotecaService servicio = new BibliotecaService();
	public static void main(String[] args) {
		SpringApplication.run(ClienteApplication.class, args);
		System.out.println("Añadir 2 usuarios");
		String[] nombres = {"Ana", "Luis"};
		String[] correos = {"correo1@correo.com", "correo2@correo.com"};
		int matricula = 1;
		for (int i = 0; i < 2; i++) {
			System.out.println("Añadiendo Usuario con matricula: " + matricula + " y nombre: " + nombres[i]);
			String aux = matricula + "";
			servicio.postUsuario(aux, nombres[i], correos[i], "1990-01-01", null, 0);
			matricula++;
		}

		servicio.getUsuario("1");
		servicio.getUsuario("2");

		//___________________________________________________________________________________________________________________________________________________________________
		//___________________________________________________________________________________________________________________________________________________________________
	
		System.out.println("Añadir dos libros");
		String[] isbn = {"1","2"};
		String[] titulos = {"Francisco Franco amo y señor de España", "Moro muerto abono para mi huerto"};
		String[] autores = {"Francisco Franco", "Miguel de Cervantes"};
		String[] ediciones = {"1", "1"};
		String[] editoriales = {"1", "1"};
		
		for (int i = 0; i < 2; i++) {
			System.out.println("Añadiendo Libro con isbn: " + isbn[i] + " y titulo: " + titulos[i]);
			servicio.postLibro(isbn[i], titulos[i], autores[i], ediciones[i], editoriales[i], 2, 2);
		}

		servicio.getLibro("1");
		servicio.getLibro("2");

		//___________________________________________________________________________________________________________________________________________________________________
		//___________________________________________________________________________________________________________________________________________________________________

		List<Prestamo> prestamo1 = new ArrayList<>();
		Usuario user1 = new Usuario("1","Ana","correo1@correo.com","1990-01-01",null,0, prestamo1, null);
		Libro libro1 = new Libro("1","Francisco Franco amo y señor de España","Francisco Franco", "1", "1", 2, 2, null);
		LocalDate fechaPrestamo = LocalDate.now();
		LocalDate fechaDevolucion = LocalDate.now();
		servicio.postPrestamo(user1, libro1, fechaPrestamo, fechaDevolucion.plusWeeks(1), false, false);

		servicio.getPrestamo(1);
		servicio.getPrestamo(2);

		//___________________________________________________________________________________________________________________________________________________________________
		//___________________________________________________________________________________________________________________________________________________________________	

		System.out.println("Actualizamos usario");
		servicio.putUsuario("1", "Ana", "correo1@correo.com", "1990-01-02", null, 1); //Cambio de fecha y porDev
		System.out.println("Actualizamos libro");
		servicio.putLibro("1","Harry Maguire y el viaje a Osasuna","Harry Maguire", "1", "1", 2, 2);
		System.out.println("Borramos el usuario 2");
		servicio.deleteUsuario("2");
		System.out.println("Borramos los libros");
		servicio.deleteLibro("2");
	}
}