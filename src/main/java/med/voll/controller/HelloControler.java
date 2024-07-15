package med.voll.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloControler {

    @GetMapping // GET /api
    public String hello() {
        return "Hello, World!";
    }

}
