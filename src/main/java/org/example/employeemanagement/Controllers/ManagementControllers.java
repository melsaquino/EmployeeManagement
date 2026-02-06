package org.example.employeemanagement.Controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.example.employeemanagement.DTOs.EmployeeDTO;
import org.example.employeemanagement.Entities.Employee;
import org.example.employeemanagement.Repositories.EmployeesRepository;
import org.example.employeemanagement.Services.GenerateAverageAge;
import org.example.employeemanagement.Services.GenerateAverageSalary;
import org.example.employeemanagement.Services.ManagementService;
import org.example.employeemanagement.Services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ManagementControllers {

    @Autowired
    EmployeesRepository employeesRepository;
    @Autowired
    private ManagementService managementService;

    @GetMapping("/employees")
    public String showHome(){
        return"employees";
    }
    @GetMapping("/api/employees")
    public ResponseEntity<List<EmployeeDTO>> displayEmployees() {

        try {
            ManagementService managementService = new ManagementService(employeesRepository);
            return ResponseEntity.ok(managementService.getAllEmployees());

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/api/add_employee")
    public String addEmployee(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("dateOfBirth") LocalDate dateBirth,
                                               @RequestParam("department") String department, @RequestParam("salary") double salary,
                                                RedirectAttributes redirectAttributes, Model model){
        ManagementService managementService;

        managementService = new ManagementService(employeesRepository);
        try{
            managementService.registerUser(id, name, dateBirth, department,salary);
            redirectAttributes.addFlashAttribute("successMessage", "User Created Successful");

            return "redirect:/employees";

        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "redirect:/employees";
        }

    }
    @PostMapping("/api/edit_employee")
    public String editEmployee(@RequestParam("edit-id") int id, @RequestParam("edit-name") String name, @RequestParam("edit-dateOfBirth") LocalDate dateBirth,
                              @RequestParam("edit-department") String department, @RequestParam("edit-salary") double salary, RedirectAttributes redirectAttributes, Model model){
        ManagementService managementService = new ManagementService(employeesRepository);

        try{
            managementService.updateEmployees(id, name, dateBirth, department,salary);
            redirectAttributes.addFlashAttribute("successMessage", "User Created Successful");
            return "redirect:/employees";

        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "redirect:/employees";
        }

    }
    @Transactional
    @DeleteMapping("/api/employees/{employeeId}")
    public String deleteEmployee(@PathVariable("employeeId") int employeeId, HttpSession session,RedirectAttributes redirectAttributes){
        System.out.println("this was triggered");
        ManagementService managementService = new ManagementService(employeesRepository);

        EmployeeDTO user = managementService.findByEmployees((String)session.getAttribute("userId"));
        if (user!=null){
            managementService.deleteEmployee(employeeId);
            redirectAttributes.addFlashAttribute("successMessage", "User Deleted Successful");
        }
        else{
            redirectAttributes.addFlashAttribute("errorMessage", "User already does not exist");

        }
        return "redirect:/employees";
    }

    @GetMapping("/api/employees/search")
    public ResponseEntity<List<EmployeeDTO>> searchByQuery(@RequestParam("query")String query,HttpSession session) {

        try {
            ManagementService managementService = new ManagementService(employeesRepository);
            return ResponseEntity.ok(managementService.findBySearchQuery(query));

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/api/employees/generate/salary")
    public ResponseEntity<Double>generateAveSalary() {

        try {
            GenerateAverageSalary generateAverageSalary= new GenerateAverageSalary(employeesRepository);
            return ResponseEntity.ok(generateAverageSalary.generate());

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/api/employees/generate/age")
    public ResponseEntity<Double>generateAveAge() {

        try {
            GenerateAverageAge generateAverageAge= new GenerateAverageAge(employeesRepository);
            return ResponseEntity.ok(generateAverageAge.generate());

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/api/employees/sorted")
    public ResponseEntity<List<EmployeeDTO>> displaySortedEmployees(@RequestParam(name="sortBy")String sortBy,@RequestParam(name = "sortOrder",defaultValue = "ASC") String sortOrder) {
        try {
            ManagementService managementService = new ManagementService(employeesRepository);
            return ResponseEntity.ok(managementService.getAllSortedEmployees(sortBy,sortOrder));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



}
