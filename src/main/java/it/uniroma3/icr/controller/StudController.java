package it.uniroma3.icr.controller;


import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.service.impl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudController {


    @GetMapping("/students")
    private String getStudents() {
        return "ciao";
    }
}
