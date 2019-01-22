package it.uniroma3.icr.validator;

import org.springframework.ui.Model;

import it.uniroma3.icr.model.Administrator;
import it.uniroma3.icr.model.Student;

import javax.servlet.http.HttpSession;


public class AdminValidator {

    public static boolean validate(Administrator admin, Model model, Administrator a, Student s,
                                   HttpSession session) {
        boolean verifica = true;

        if (admin.getUsername().equals("")) {
            verifica = false;
            session.setAttribute("errUsername", "*Campo obbligatorio");
            model.addAttribute("errUsername", "*Campo obbligatorio");
        }
        if (admin.getPassword().equals("")) {
            verifica = false;
            session.setAttribute("errPassword", "*Campo obbligatorio");
            model.addAttribute("errPassword", "*Campo obbligatorio");
        }

        if (a != null || s != null) {
            verifica = false;
            model.addAttribute("usernameError", "Username gia' esistente");

        }


        return verifica;
    }
}