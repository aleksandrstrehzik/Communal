package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final HttpSession session;

    @GetMapping("/")
    public String homePage(Model model, Principal principal) {
        String s = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (s.equals("[ADMIN]")) {
            model.addAttribute("role", 1);
            String name = principal.getName();
            session.setAttribute("roleValue", 1);
            session.setAttribute("currentUserName", name);
        }
        if (s.equals("[OPERATOR]")) {
            model.addAttribute("role", 2);
            String name = principal.getName();
            session.setAttribute("roleValue", 2);
            session.setAttribute("currentUserName", name);
        }
        if (s.equals("[CONSUMER]")) {
            model.addAttribute("role", 3);
            String name = principal.getName();
            session.setAttribute("roleValue", 3);
            session.setAttribute("currentUserName", name);
        }
        return "home-page";
    }

    @Bean
    public PasswordEncoder getEncoderBean() {
        return new BCryptPasswordEncoder();
    }
}
