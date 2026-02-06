package org.example.employeemanagement.Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

   // @Autowired
    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    /**
     * Used to show login.html to user
     * */
    @GetMapping("/login")
    public String ShowLoginPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session!=null && session.getAttribute("loginError")!=null && (boolean)session.getAttribute("loginError")){
            model.addAttribute("errorMessage","Invalid Email or Password!");
            request.getSession().removeAttribute("loginError");

        }
        return "login";
    }

    /**
     * Processes logout
     *
     * */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws ServletException {

        HttpSession session = request.getSession(false);
        String errorMessage = (String) session.getAttribute("loginError");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logoutHandler.logout(request, response, auth);
        }
        return "redirect:/login"; // Redirect to a login page or confirmation
    }
}
