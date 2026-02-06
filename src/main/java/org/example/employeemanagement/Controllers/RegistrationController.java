package org.example.employeemanagement.Controllers;

import jakarta.servlet.http.HttpSession;
import org.example.employeemanagement.Repositories.AdminsRepository;
import org.example.employeemanagement.Repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.example.employeemanagement.Services.RegistrationService;

import java.time.LocalDate;
import java.util.Date;

@Controller
public class RegistrationController {
    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private AdminsRepository adminsRepository;

    /**
     * Shows the registration
     * */
    @GetMapping("/registration")
    public String ShowRegistrationPage(HttpSession session){
        if (session != null &&  session.getAttribute("loggedIn")!=null && ((boolean) session.getAttribute("loggedIn"))) {
            return "redirect:/";
        }
        return "registration";
    }

    /**
     * Controller that triggers the registration service that will make the user
     *
     * */
    @PostMapping("/registration")
    public String createUser(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("dateOfBirth") LocalDate dateBirth,
                             @RequestParam("department") String department, @RequestParam("salary") double salary, @RequestParam("password") String password,
                             @RequestParam("psw_repeat") String psw_repeat, RedirectAttributes redirectAttributes, Model model){
        RegistrationService registrationService;

        registrationService = new RegistrationService(adminsRepository);
        try{
            registrationService.registerUser(id, name, dateBirth, department,salary,password,psw_repeat );
            redirectAttributes.addFlashAttribute("successMessage", "User Created Successful");

            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "/registration";
        }

    }
}
