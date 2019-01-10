package it.uniroma3.icr.supportControllerMethod;

import it.uniroma3.icr.instagramConfig.UserInstagram;
import it.uniroma3.icr.model.StudentSocial;
import it.uniroma3.icr.service.impl.StudentServiceSocial;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstagramControllerSupport {

    private SetSchools setSchools = new SetSchools();

    public String instagramLogin(Model model, Long id, StudentServiceSocial userFacadeSocial,
                                 UserInstagram userInstagram) {
        StudentSocial student = userFacadeSocial.findUser(String.valueOf(id));
        if (student != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
            List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
            updatedAuthorities.add(authority);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(student.getUsername(),
                    "", updatedAuthorities);
            auth.setDetails(student);
            SecurityContextHolder.getContext().setAuthentication(auth);
            model.addAttribute("student", student);
            return "redirect:/user/homeStudent";
        } else {
            String name;
            String surname;
            String[] fullName;

            if (userInstagram.getName() == null && userInstagram.getSurname() == null) {
                fullName = userInstagram.getFullName().split(" ");
                name = fullName[0];
                surname = fullName[1];
                model.addAttribute("nome", name);
                model.addAttribute("cognome", surname);

            }

            model.addAttribute("id", id);
            model.addAttribute("student", new StudentSocial());

            Map<String, String> schools = setSchools.setSchools();
            model.addAttribute("schools", schools);

            Map<String, String> schoolGroups = new HashMap<String, String>();
            schoolGroups.put("3", "3");
            schoolGroups.put("4", "4");
            schoolGroups.put("5", "5");
            model.addAttribute("schoolGroups", schoolGroups);

            return "/registrationInstagram";
        }
    }

}
