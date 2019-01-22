package it.uniroma3.icr.validator;

import org.springframework.ui.Model;

import it.uniroma3.icr.model.Job;

import javax.servlet.http.HttpSession;


public class jobValidator {

    public static boolean validate(Job job, Model model, HttpSession session) {
        boolean verifica = true;
        if (job.getTitle().equals("")) {
            verifica = false;
            session.setAttribute("errTitle", "*Campo obbligatorio");
            model.addAttribute("errTitle", "*Campo obbligatorio");
        }
        if (job.getNumberOfStudents() == null || job.getStudents() == 0) {
            verifica = false;
            session.setAttribute("errStudenti", "*Questo campo non puo' essere nullo o zero");
            model.addAttribute("errStudenti", "*Questo campo non puo' essere nullo o zero");
        }
        if(job.getDescription().equals("")) {
            verifica = false;
            session.setAttribute("errDescription","*Campo Obbligatorio");
            model.addAttribute("errDescription","*Campo Obbligatorio");
        }
        return verifica;

    }


}