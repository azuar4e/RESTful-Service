package es.upm.sos.biblioteca;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.upm.sos.biblioteca.models.Usuario;
import es.upm.sos.biblioteca.models.Prestamo;

@SpringBootApplication
public class BibliotecaApplication {

	private static final String API_URL = "http://localhost:8080/biblioteca.api";

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
		// Scanner scanner = new Scanner(System.in);
		// List<Prestamo> prestamos = new ArrayList();
		// String rama;

		// System.out.println("Bienvenido a el codigo del cliente. Indica escribe que deseas hacer: Usuarios | Libros | Prestamo ");
		// rama = scanner.nextLine();
		// if(rama.equals("Usuarios")){
		// 	System.out.println("Estas dentro de usuarios ¿que deseas hacer?: Registrar Usuario | Lista de usuarios | Actualizar Usuario | Ver usuario | Eliminar usuario");
		// 	rama = scanner.nextLine();
		// 	if(rama.equals("Registrar Usuario")){

		// 	}
		// 	else if(rama.equals("Lista de usuarios")){

		// 	}
		// 	else if(rama.equals()){

		// 	}
		// 	else if(rama.equals()){

		// 	}
		// 	else if(rama.equals()){

		// 	}
		// 	System.out.println("===Registra un usuario nuevo===");
		// 	System.out.println("Matricula: ");
		// 	String matricula = scanner.nextLine();

		// 	System.out.println("Nombre: ");
		// 	String nombre = scanner.nextLine();

		// 	System.out.println("Correo: ");
		// 	String correo = scanner.nextLine();

		// 	System.out.println("Fecha de nacimiento: ");
		// 	String fechaNacimiento = scanner.nextLine();

		// 	Usuario user = new Usuario(matricula, nombre, correo, fechaNacimiento, null, 0, prestamos);
		// }

		// HttpClient client = HttpClient.newHttpClient();
		// HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_URL + "/users")).GET().build();

	}

}
