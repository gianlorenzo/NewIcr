package it.uniroma3.icr.validator;

import org.springframework.ui.Model;

import it.uniroma3.icr.model.Administrator;
import it.uniroma3.icr.model.StudentSocial;

import javax.servlet.http.HttpSession;


public class StudentValidator2 {

    public static boolean validate(StudentSocial student, Model model,
                                   HttpSession session) {
        boolean verifica = true;

        if (student.getName().equals("")) {
            verifica = false;
            session.setAttribute("errName", "*Campo Obbligatorio");
            model.addAttribute("errName", "*Campo obbligatorio");
        }
        if (student.getSurname().equals("")) {
            verifica = false;
            session.setAttribute("errSurname", "*Campo Obbligatorio");
            model.addAttribute("errSurname", "*Campo obbligatorio");
        }

        if (student.getSection().equals("")) {
            verifica = false;
            session.setAttribute("errSection", "*Campo Obbligatorio");
            model.addAttribute("errSection", "*Campo obbligatorio");
        }

        if (student.getEmail().equals("")) {
            verifica = false;
            session.setAttribute("errEmail", "*Campo Obbligatorio");
            model.addAttribute("errEmail", "*Campo obbligatorio");
        }
        return verifica;
        }
}



