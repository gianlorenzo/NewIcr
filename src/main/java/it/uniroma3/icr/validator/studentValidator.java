package it.uniroma3.icr.validator;

import org.springframework.ui.Model;

import it.uniroma3.icr.model.Administrator;
import it.uniroma3.icr.model.Student;

import javax.servlet.http.HttpSession;


public class studentValidator {

    public static boolean validate(Student student, Model model, Student u, Administrator a, HttpSession session) {
        boolean verifica = true;

        session.setAttribute("u",u);

        if (student.getName().equals("")) {
            verifica = false;
            session.setAttribute("errName","*Campo Obbligatorio");
            model.addAttribute("errName", "*Campo obbligatorio");
        }
        if (student.getSurname().equals("")) {
            verifica = false;
            session.setAttribute("errSurname","*Campo Obbligatorio");
            model.addAttribute("errSurname", "*Campo obbligatorio");
        }
        if (student.getSchool().equals("")) {
            verifica = false;
            session.setAttribute("errSchool","*Campo Obbligatorio");
            model.addAttribute("errSchool", "*Campo obbligatorio");
        }
        if (student.getSection().equals("")) {
            verifica = false;
            session.setAttribute("errSection","*Campo Obbligatorio");
            model.addAttribute("errSection", "*Campo obbligatorio");
        }
        if (student.getUsername().equals("")) {
            verifica = false;
            session.setAttribute("errUsername","*Campo Obbligatorio");
            model.addAttribute("errUsername", "*Campo obbligatorio");
        }
        if (student.getPassword().equals("")) {
            verifica = false;
            session.setAttribute("errPassword","*Campo Obbligatorio");
            model.addAttribute("errPassword", "*Campo obbligatorio");
        }
        if (student.getEmail().equals("")) {
            verifica = false;
            session.setAttribute("errEmail","*Campo Obbligatorio");
            model.addAttribute("errEmail", "*Campo obbligatorio");
        }

        if (u != null || a != null) {
            verifica = false;
            session.setAttribute("usernameError","Username gia' esistente");
            model.addAttribute("usernameError", "Username gia' esistente");
        }

        return verifica;
    }

}
