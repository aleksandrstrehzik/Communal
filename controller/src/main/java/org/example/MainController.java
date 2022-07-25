package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collection;

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
}
