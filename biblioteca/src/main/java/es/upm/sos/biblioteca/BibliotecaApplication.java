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
	}

}
