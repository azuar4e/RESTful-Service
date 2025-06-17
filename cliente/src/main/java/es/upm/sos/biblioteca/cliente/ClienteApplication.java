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
		String[] isbn = {"1","2","3", "4"};
		String[] titulos = {"King pantera", "Amongus","Terrence McKenna", "pedro sanchez ~ final electrica"};
		String[] autores = {"King", "Miguel de Cervantes", "Abderraman III", "Kevin Roldan"};
		String ediciones = "1";
		String editoriales = "1";
		
		for (int i = 0; i < 4; i++) {
			System.out.println("Añadiendo Libro con isbn: " + isbn[i] + " y titulo: " + titulos[i]);
			servicio.postLibro(isbn[i], titulos[i], autores[i], ediciones, editoriales, 2, 2);
		}

		servicio.getLibro("1");
		servicio.getLibro("2");

		servicio.getPorTitulo("pedro");
		Integer unidades = servicio.getLibroUnidades("1");
		System.out.println("Unidades del libro con isbn 1: " + unidades);

		//___________________________________________________________________________________________________________________________________________________________________
		//___________________________________________________________________________________________________________________________________________________________________

		List<Prestamo> prestamo1 = new ArrayList<>();
		Usuario user1 = new Usuario("1","Ana","correo1@correo.com","1990-01-01",null,0, prestamo1, null);
		Libro libro1 = new Libro("1", titulos[0], autores[0], "1", "1", 2, 2, null);
		Libro libro2 = new Libro("2",titulos[1], autores[1], "1", "1", 2, 2, null);
		Libro libro3 = new Libro("3",titulos[2], autores[2], "1", "1", 2, 2, null);

		LocalDate fechaPrestamo = LocalDate.now();
		LocalDate fechaDevolucion = LocalDate.now();
		servicio.postPrestamo(user1, libro1, fechaPrestamo, fechaDevolucion.plusWeeks(1), false, false);
		servicio.postPrestamo(user1, libro2, fechaPrestamo, fechaDevolucion.plusWeeks(1), false, false);
		servicio.postPrestamo(user1, libro3, fechaPrestamo.minusMonths(1), fechaDevolucion.minusMonths(1), false, false);

		servicio.getPrestamo(1);

		//___________________________________________________________________________________________________________________________________________________________________
		//___________________________________________________________________________________________________________________________________________________________________	

		System.out.println("Actualizamos usario");
		servicio.putUsuario("1", "Ana", "correo1@correo.com", "1990-01-02", null, 1); //Cambio de fecha y porDev
		System.out.println("Actualizamos libro");
		servicio.putLibro("1","Harry Maguire y el viaje a Osasuna","Harry Maguire", "1", "1", 2, 2);
		System.out.println("Borramos el usuario 2");
		servicio.deleteUsuario("2");

		System.out.println("Obtenemos los usuarios");
		servicio.getUsuarios(0, 3);

		System.out.println("Obtenemos los Libros");
		servicio.getLibros(0, 3);

		System.out.println("Obtenemos los prestamos");
		servicio.getPrestamos(0, 3);

		System.out.println("Obtenemos los prestamos de un usuario");
		servicio.getPrestamosUsuario("1", null, null, false, 0, 3);

		System.out.println("Devolvemos un libro\n\t\\________Verificar la uri: http://localhost:8080/biblioteca.api/prestamos/1");
		servicio.putPrestamoDevolverLibro(1);

		// System.out.println("Verificamos la devolucion de un prestamo caducado\n\t\\________Verificar la uri: http://localhost:8080/biblioteca.api/prestamos/3"+
		// "\n\t\\________Verificar la uri: http://localhost:8080/biblioteca.api/users/1");
		// servicio.putVerificarDevolucion(3);

		// System.out.println("En la anterior consulta se deberia poner el numero de libros por devolver"
		// + " del usuario a 1 y el verificar devolucion del prestamo a true");

		// System.out.println("Devolvemos ese libro pendiente"+
		// "\n\t\\________Verificar la uri: http://localhost:8080/biblioteca.api/users/1"+
		// "\n\t\\________Verificar la uri: http://localhost:8080/biblioteca.api/prestamos/3");
		// servicio.putPrestamoDevolverLibro(3);

		System.out.println("En la anterior consulta se deberia poner el numero de libros por devolver"
		+ " del usuario a 0, la devolucion del prestamo a true y y una sancion de 1 semana al usuario a partir de ahora");

		System.out.println("Obtenemos los prestamos de un usuario");
		servicio.getPrestamosUsuario("1", null, null ,false, 0, 3);

		System.out.println("Obtenemos los prestamos de un usuario");
		servicio.getPrestamosUsuario("2", null, null ,false, 0, 3);

		System.out.println("Obtenemos los prestamos por fecha de prestamo");
		servicio.getPrestamosUsuario("1", LocalDate.now(), null, false, 0, 3);

		System.out.println("Obtenemos los prestamos por fecha de devolucion");
		servicio.getPrestamosUsuario("1", null, LocalDate.now(), false, 0, 3);		

		System.out.println("Obtenemos los prestamos entre fechas");
		servicio.getPrestamosUsuario("1", LocalDate.now(), LocalDate.now().plusWeeks(2),false, 0, 3);		

		System.out.println("Obtenemos los prestamos devueltos");
		servicio.getPrestamosUsuario("1", null, null, true, 0, 3);		

		System.out.println("Obtenemos la actividad de un usuario");
		servicio.getActividadUsuarioLink("1");

		System.out.println("Hacemos un aumento de plazo de prestamo");
		servicio.ampliarPrestamo("1",2);

		// System.out.println("Borramos los libros");
		// servicio.deleteLibro("1");
		// servicio.deleteLibro("2");
		// servicio.deleteLibro("4");

		// System.out.println("Borramos los prestamos");
		// servicio.deletePrestamo(1);
		// servicio.deletePrestamo(2);
		// servicio.deletePrestamo(3);

		// System.out.println("Borramos el usuario");
		// servicio.deleteUsuario("1");

		
	}
}