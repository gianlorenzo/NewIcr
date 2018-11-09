package it.uniroma3.icr.supportControllerMethod;

import it.uniroma3.icr.model.StudentSocial;
import it.uniroma3.icr.service.impl.StudentFacadeSocial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacebookControllerSupport {

    private SetSchools setSchools = new SetSchools();

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public String facebookLogin(Facebook facebook, StudentFacadeSocial userFacadesocial,
                                Model model, String social, RedirectAttributes redirectAttributes) {
        String[] fields = { "first_name", "last_name", "email" };
        User user = facebook.fetchObject("me", User.class, fields);
        String email = user.getEmail();
        String id = user.getId();
        StudentSocial student = userFacadesocial.findUser(id);
        if (student != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
            List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
            updatedAuthorities.add(authority);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(student.getUsername(),
                    "", updatedAuthorities);
            auth.setDetails(student);
            SecurityContextHolder.getContext().setAuthentication(auth);
            model.addAttribute("student", student);
            LOGGER.info("Login: " + student.toString());
            social = "fb";
            redirectAttributes.addFlashAttribute("social", social);
            return "redirect:/user/homeStudent";
        }
        String name = user.getFirstName();
        String surname = user.getLastName();
        model.addAttribute("nome", name);
        model.addAttribute("cognome", surname);
        model.addAttribute("email", email);
        model.addAttribute("id", id);
        model.addAttribute("student", new StudentSocial());
        Map<String, String> schools = setSchools.setSchools();
        model.addAttribute("schools", schools);
        Map<String, String> schoolGroups = new HashMap<String, String>();
        schoolGroups.put("3", "3");
        schoolGroups.put("4", "4");
        schoolGroups.put("5", "5");
        model.addAttribute("schoolGroups", schoolGroups);
        return "/registrationFacebook";
    }


}
