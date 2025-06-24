package es.upm.sos.biblioteca.cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import es.upm.sos.biblioteca.cliente.models.*;
import es.upm.sos.biblioteca.cliente.service.BibliotecaService;

@SpringBootApplication
public class ClienteApplication {
	static BibliotecaService servicio = new BibliotecaService();
	public static void main(String[] args) {
		System.out.println("===== INICIO DEL TEST DE OPERACIONES =====");
		SpringApplication.run(ClienteApplication.class, args);
		System.out.println("\n\n[+] POST de Usuarios\n");
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

		System.out.println("\n\n[+] GET de Usuarios\n");
		System.out.println("\t[-] GET de Usuario con matricula 1");
		servicio.getUsuario("1");
		System.out.println("\n\t[-] GET de Usuario con matricula 2");
		servicio.getUsuario("2");

		//___________________________________________________________________________________________________________________________________________________________________
		//___________________________________________________________________________________________________________________________________________________________________
	
		System.out.println("\n\n[+] POST de libros\n");
		System.out.println("Añadir cuatro libros");
		String[] isbn = {"1","2","3", "4"};
		String[] titulos = {"King pantera", "Amongus","Terrence McKenna", "pedro sanchez ~ final electrica"};
		String[] autores = {"King", "Miguel de Cervantes", "Abderraman III", "Kevin Roldan"};
		String ediciones = "1";
		String editoriales = "1";
		
		for (int i = 0; i < 4; i++) {
			System.out.println("Añadiendo Libro con isbn: " + isbn[i] + " y titulo: " + titulos[i]);
			servicio.postLibro(isbn[i], titulos[i], autores[i], ediciones, editoriales, 2, 2);
		}

		System.out.println("\n\n[+] GET de Libros\n");
		System.out.println("\n\t[-] GET de Libros con isbn 1");
		servicio.getLibro("1");
		System.out.println("\n\t[-] GET de Libros con isbn 4");
		servicio.getLibro("4");

		System.out.println("\n\t[-] GET de Libros con filtrado por titulo => 'pedro'");
		servicio.getPorTitulo("pedro");
		System.out.println("\n\t[-] GET unidades del Libro con isbn 1");
		Integer unidades = servicio.getLibroUnidades("1");
		System.out.println("Unidades del libro con isbn 1: " + unidades);

		//___________________________________________________________________________________________________________________________________________________________________
		//___________________________________________________________________________________________________________________________________________________________________

		System.out.println("\n\n[+] POST de prestamos\n");
		List<Prestamo> prestamo1 = new ArrayList<>();
		Usuario user1 = new Usuario("1","Ana","correo1@correo.com","1990-01-01",null,0, prestamo1, null);
		Libro libro1 = new Libro("1", titulos[0], autores[0], "1", "1", 2, 2, null);
		Libro libro2 = new Libro("2",titulos[1], autores[1], "1", "1", 2, 2, null);
		Libro libro3 = new Libro("3",titulos[2], autores[2], "1", "1", 2, 2, null);

		LocalDate fechaPrestamo = LocalDate.now();
		LocalDate fechaDevolucion = LocalDate.now();
		int[] ids = new int[3];

		System.out.println("Añadiendo Prestamo con usuario: " + user1.getMatricula() + " y libro: " + libro1.getIsbn());
		ids[0] = servicio.postPrestamo(user1, libro1, fechaPrestamo, fechaDevolucion.plusWeeks(1), false, false, false);
		System.out.println("Añadiendo Prestamo con usuario: " + user1.getMatricula() + " y libro: " + libro2.getIsbn());
		ids[1] = servicio.postPrestamo(user1, libro2, fechaPrestamo, fechaDevolucion.plusWeeks(1), false, false, false);
		System.out.println("Añadiendo Prestamo con usuario: " + user1.getMatricula() + " y libro: " + libro3.getIsbn());
		ids[2] = servicio.postPrestamo(user1, libro3, fechaPrestamo.minusMonths(1), fechaDevolucion.minusMonths(1), false, false, false);

		System.out.println("\n\n[+] GET de Prestamos\n");
		servicio.getPrestamo(ids[0]);

		//___________________________________________________________________________________________________________________________________________________________________
		//___________________________________________________________________________________________________________________________________________________________________
		
		System.out.println("\n\n[+] PUT sobre un usuario");
		System.out.println("\t\\________Actualizamos usario");
		servicio.putUsuario("1", "Ana", "correo1@correo.com", "1990-01-02", null, 1);
		System.out.println("\n\n[+] PUT sobre un libro");
		System.out.println("\t\\________Actualizamos libro");
		servicio.putLibro("1","Harry Maguire y el viaje a Osasuna","Harry Maguire", "1", "1", 2, 2);
		System.out.println("\n\n[+] DELETE sobre un usuario");
		System.out.println("\t\\________Borramos el usuario 2");
		servicio.deleteUsuario("2");

		System.out.println("\n\n[+] GET de todos los Usuarios\n");
		System.out.println("\t\\________Obtenemos los usuarios");
		servicio.getUsuarios(0, 3);

		System.out.println("\n\n[+] GET de todos los Libros\n");
		System.out.println("\t\\________Obtenemos los Libros");
		servicio.getLibros(0, 3);

		System.out.println("\n\n[+] GET de todos los Prestamos\n");
		System.out.println("\t\\________Obtenemos los prestamos");
		servicio.getPrestamos(0, 3);

		System.out.println("\n\n[+] GET de todos los Prestamos de un Usuario\n");
		System.out.println("\t\\________Obtenemos los prestamos de un usuario");
		servicio.getPrestamosUsuario("1", null, null, false, 0, 3);



		System.out.println("\n\n[+] PUT sobre prestamos: ampliar, devolver, verificar devolución");
		System.out.println("\t[-] Ampliamos un préstamo");
		servicio.putAmpliarPrestamo(ids[0], user1, libro1, LocalDate.now(), fechaDevolucion.plusWeeks(1), false, false, true);

		System.out.println("\n\t[+] Llamadas erróneas para la ampliación de un préstamo");
		System.out.println("\t\t\\________Tratamos de ampliar un préstamo cuya fecha de devolución ya ha expirado. Esta operación espera un error 400 Bad Request");
		servicio.putAmpliarPrestamo(ids[2], user1, libro3, fechaPrestamo.minusMonths(1), fechaDevolucion.minusMonths(1), false, false, true);

		System.out.println("\t\t\\________Tratamos de ampliar un préstamo que no existe. Esta operación espera un error 404 Not Found");
		servicio.putAmpliarPrestamo(ids[2]+1, user1, libro3, fechaPrestamo.minusMonths(1), fechaDevolucion.minusMonths(1), false, false, true);

//		System.out.println("\t\t\\________Tratamos de ampliar un préstamo con el campo 'ampliar' a false. Esta operación espera un error 400 Bad Request");
//		servicio.putAmpliarPrestamo(ids[2], user1, libro3, fechaPrestamo.minusMonths(1), fechaDevolucion.minusMonths(1), false, false, false);




		System.out.println("\t[-] Devolvemos un préstamo");
		servicio.putPrestamo(ids[1], user1, libro2, fechaPrestamo, fechaDevolucion.plusWeeks(1), true, false, false);

		System.out.println("\t[-] Llamadas erróneas para la devolución de un préstamo");
		System.out.println("\t\t\\________Tratamos de devolver ese mismo préstamo (que ya está devuelto). Esta operación espera un error 409 Conflict");
		servicio.putPrestamo(ids[1], user1, libro2, fechaPrestamo, fechaDevolucion.plusWeeks(1), true, false, false);
		//****************************************************
		System.out.println("\t\t\\________Introducimos mal algún parámetro. Esta operación espera un error 400 Bad Request");
		servicio.putPrestamo(ids[0], user1, libro3, fechaPrestamo, fechaDevolucion.plusWeeks(3), true, false, false);

		System.out.println("\t\t\\________Introducimos el id de un préstamo que no existe. Esta operación espera un error 404 Not Found");
		servicio.putPrestamo(ids[2] + 1, user1, libro1, fechaPrestamo, fechaDevolucion.plusWeeks(1), true, false, false);





		System.out.println("\n\t[-] Verificamos la devolución de un prestamos");
		servicio.putPrestamo(ids[2], user1, libro3, fechaPrestamo.minusMonths(1), fechaDevolucion.minusMonths(1), false, true, false);

		System.out.println("\t[-] Llamadas erróneas para la verificación de la devolución de un préstamo");
		System.out.println("\t\t\\________Tratamos de verificar ese mismo préstamo (que ya está verificado). Esta operación espera un error 409 Conflict");
		servicio.putPrestamo(ids[2], user1, libro3, fechaPrestamo.minusMonths(1), fechaDevolucion.minusMonths(1), false, true, false);

		System.out.println("\t\t\\________Introducimos mal algún parámetro. Esta operación espera un error 400 Bad Request");
		servicio.putPrestamo(ids[2], user1, libro3, fechaPrestamo.minusMonths(3), fechaDevolucion.minusMonths(1), false, true, false);

		System.out.println("\t\t\\________Introducimos el id de un préstamo que no existe. Esta operación espera un error 404 Not Found");
		servicio.putPrestamo(ids[2] + 1, user1, libro3, fechaPrestamo.minusMonths(1), fechaDevolucion.minusMonths(1), false, true, false);


		System.out.println("Solicitamos un prestamo con el usuario con libros por devolver.");
		servicio.postPrestamo(user1, libro1, fechaPrestamo, fechaDevolucion.plusWeeks(1), false, false, false);
		System.out.println("Devolvemos el libro y reintentamos la solicitud");
		servicio.putPrestamo(ids[2], user1, libro3, fechaPrestamo.minusMonths(1), fechaDevolucion.minusMonths(1), true, false, false);
		servicio.postPrestamo(user1, libro1, fechaPrestamo, fechaDevolucion.plusWeeks(1), false, false, false);
		System.out.println("Ambas llamadas deberían haber devuelto un error, y si ahora hacemos un GET del user, este debería tener una sanción aplicada.");


		System.out.println("\n\n");
		System.out.println("Obtenemos los prestamos del usuario 1");
		servicio.getPrestamosUsuario("1", null, null ,false, 0, 3);

		try {
			System.out.println("Obtenemos los prestamos del usuario 2, que hemos borrado antes, por lo que debería dar un 404 Not Found");
			servicio.getPrestamosUsuario("2", null, null ,false, 0, 3);
		} catch (RuntimeException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("\n\n[+] GET de los Prestamos de un Usuario filtrados\n");
		System.out.println("Obtenemos los prestamos por fecha de préstamo");
		servicio.getPrestamosUsuario("1", LocalDate.now(), null, false, 0, 3);

		System.out.println("Obtenemos los prestamos por fecha de devolución");
		servicio.getPrestamosUsuario("1", null, LocalDate.now(), false, 0, 3);

		System.out.println("Obtenemos los prestamos por fecha de préstamo y fecha de devolución");
		servicio.getPrestamosUsuario("1", LocalDate.now(), LocalDate.now().plusWeeks(2),false, 0, 3);

		System.out.println("\n\n[+] GET de los Prestamos de un Usuario que han sido devueltos, esto es, el histórico\n");
		System.out.println("Obtenemos los prestamos devueltos");
		servicio.getPrestamosUsuario("1", null, null, true, 0, 3);

		System.out.println("\n\n[+] GET de la actividad de un Usuario\n");
		System.out.println("Obtenemos la actividad de un usuario");
		servicio.getActividadUsuarioLink("1");


		System.out.println("\n\n[+] DELETE de los Prestamos\n");
		servicio.deletePrestamo(ids[0]);
		servicio.deletePrestamo(ids[1]);
		servicio.deletePrestamo(ids[2]);

		System.out.println("\n\n[+] DELETE de los Libros\n");
		servicio.deleteLibro("1");
		servicio.deleteLibro("2");
		servicio.deleteLibro("3");
		servicio.deleteLibro("4");

		System.out.println("\n\n[+] DELETE del Usuario\n");
		servicio.deleteUsuario("1");

		System.out.println("\n===== FIN DEL TEST =====");

	}
}