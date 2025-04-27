package es.upm.sos.biblioteca.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public String home() {
        return "API Biblioteca activa";
    }

    @GetMapping("/biblioteca.api")
    public String biblioteca() {
        return "API Biblioteca activa";
    }
}