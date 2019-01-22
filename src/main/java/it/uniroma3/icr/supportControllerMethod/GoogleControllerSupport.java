package it.uniroma3.icr.supportControllerMethod;

import it.uniroma3.icr.model.StudentSocial;
import it.uniroma3.icr.service.impl.StudentServiceSocial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.google.api.Google;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleControllerSupport {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    private SetSchools setSchools = new SetSchools();

    public String googleLogin(Google google, ConnectionRepository connectionRepository,
                              StudentServiceSocial userFacadesocial, Model model,
                              String social, RedirectAttributes redirectAttributes, HttpSession session) {
        String id = google.userOperations().getUserInfo().getId();
        StudentSocial student = userFacadesocial.findUser(id);

        if (student != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
            List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
            updatedAuthorities.add(authority);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    student.getUsername(), "", updatedAuthorities);
            auth.setDetails(student);
            SecurityContextHolder.getContext().setAuthentication(auth);
            model.addAttribute("student", student);
            LOGGER.info("Login: " + student.toString());
            social = "goo";
            redirectAttributes.addFlashAttribute("social", social);
            return "redirect:/user/homeStudent";
        } else {
            String name = google.userOperations().getUserInfo().getFirstName();
            String surname = google.userOperations().getUserInfo().getLastName();
            String email = google.userOperations().getUserInfo().getEmail();
            StudentSocial s = new StudentSocial();
            if(name != null) {
                s.setName(name);
            }
            if(surname!=null) {
                s.setSurname(surname);
            }
            s.setEmail(email);
            s.setUsername(id);
            session.setAttribute("name",name);
            session.setAttribute("surname",surname);
            session.setAttribute("email",email);
            session.setAttribute("id",id);
            session.setAttribute("student", s);
            model.addAttribute("name", name);
            model.addAttribute("surname", surname);
            model.addAttribute("email", email);
            model.addAttribute("id", id);
            model.addAttribute("student", s);

            Map<String, String> schools = setSchools.setSchools();
            model.addAttribute("schools", schools);
            session.setAttribute("schools", schools);

            Map<String, String> schoolGroups = new HashMap<String, String>();
            schoolGroups.put("3", "3");
            schoolGroups.put("4", "4");
            schoolGroups.put("5", "5");
            model.addAttribute("schoolGroups", schoolGroups);
            session.setAttribute("schoolGroups", schoolGroups);
            return "/registrationGoogle";
        }
    }

}
