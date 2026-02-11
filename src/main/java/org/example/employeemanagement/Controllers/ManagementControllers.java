package org.example.employeemanagement.Controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.example.employeemanagement.DTOs.EmployeeDTO;
import org.example.employeemanagement.Entities.Employee;
import org.example.employeemanagement.Exceptions.InvalidBirthDateException;
import org.example.employeemanagement.Repositories.AccountingRepository;
import org.example.employeemanagement.Repositories.EmployeesRepository;
import org.example.employeemanagement.Repositories.HRsRepository;
import org.example.employeemanagement.Repositories.ITsRepository;
import org.example.employeemanagement.Services.*;
import org.example.employeemanagement.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ManagementControllers {
    private static final Logger LOGGER = Logger.getLogger(ManagementControllers.class.getName());

    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private HRsRepository hrRepository;
    @Autowired
    private ITsRepository itRepository;
    @Autowired
    private AccountingRepository accountingRepository;


    private final ResourceBundle bundle = ResourceBundle.getBundle("messages-en");
    @GetMapping("/")
    public String showDefault(){
        return"redirect:"+Config.HOME_URL;
    }

    @GetMapping("${url.home}")
    public String showHome(){
        LOGGER.info("Accessed GET '/employees'");
        return "employees";
    }
    @GetMapping("${url.employees.api}")
    public ResponseEntity<List<EmployeeDTO>> displayEmployees(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5")int size) {
        LOGGER.info("Accessed GET '/api/employees?page="+page+"&size="+size+"'");

        try {
            ManagementService managementService = new ManagementService(employeesRepository);
            LOGGER.info("Data successfully accessed");
            return ResponseEntity.ok(managementService.getAllEmployees(page,size));

        } catch (Exception e) {
            LOGGER.severe("Caught an Exception: "+e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("${url.add}")
    public String addEmployee(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("dateOfBirth") String dateBirth,
                                               @RequestParam("department") String department, @RequestParam("salary") String salary,
                                                RedirectAttributes redirectAttributes, Model model){
        LOGGER.info("Accessed POST '/api/add_employee'");

        ModifyDBServices modifyDBServices;

        modifyDBServices= new ModifyDBServices(employeesRepository,hrRepository,itRepository,accountingRepository);
        try{
            modifyDBServices.registerUser(Integer.parseInt(id), name, LocalDate.parse(dateBirth), department, Double.parseDouble(salary));

            String message = bundle.getString("success.userCreation");
            redirectAttributes.addFlashAttribute("successMessage", message);
            LOGGER.info("An employee was successfully added");
            return"redirect:"+Config.HOME_URL;

        } catch (Exception e) {
            String message;
            if (e instanceof NumberFormatException)
                message = bundle.getString("invalid.number");
            else if (e instanceof DateTimeParseException)
                message = bundle.getString("invalid.date");
            else message= e.getMessage();

            LOGGER.severe("Caught an Exception: "+e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", message);

            return"redirect:"+Config.HOME_URL;
        }

    }
    @PostMapping("${url.edit}")
    public String editEmployee(@RequestParam(name= "edit-id") String id, @RequestParam("edit-name") String name, @RequestParam("edit-dateOfBirth") String dateBirth,
                              @RequestParam(name="edit-department") String department, @RequestParam("edit-salary") String salary, RedirectAttributes redirectAttributes, Model model){
        LOGGER.info("Accessed POST '/api/edit_employee'");

        ModifyDBServices modifyDBServices;
        String message;
        modifyDBServices= new ModifyDBServices(employeesRepository,hrRepository,itRepository,accountingRepository);
        try{
            modifyDBServices.updateEmployees(Integer.parseInt(id), name, LocalDate.parse(dateBirth), department, Double.parseDouble(salary));
            message = bundle.getString("success.userUpdate");

            redirectAttributes.addFlashAttribute("successMessage", message);
            LOGGER.info("A user was successfully edited");
            return"redirect:"+Config.HOME_URL;

        } catch (Exception e) {
            if (e instanceof NumberFormatException)
                message = bundle.getString("invalid.number");
            else if (e instanceof DateTimeParseException)
                message = bundle.getString("invalid.date");

            else
                message = e.getMessage();

            redirectAttributes.addFlashAttribute("errorMessage", message);
            LOGGER.severe("Caught an Exception: "+e.getMessage());
            return"redirect:"+Config.HOME_URL;
        }

    }
    @Transactional
    @DeleteMapping("${url.delete}/{employeeId}")
    public String deleteEmployee(@PathVariable("employeeId") int employeeId, HttpSession session,RedirectAttributes redirectAttributes){
        LOGGER.info("Accessed DELETE '/api/employees/"+employeeId+"'");
        ModifyDBServices modifyDBServices;
        String message;
        modifyDBServices= new ModifyDBServices(employeesRepository,hrRepository,itRepository,accountingRepository);
        try{
            modifyDBServices.deleteEmployee(employeeId);
            message = bundle.getString("success.userDelete");
            redirectAttributes.addFlashAttribute("successMessage", message);
            LOGGER.info("A user was successfully deleted");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            LOGGER.severe("Caught an Exception: " + e.getMessage());
        }
        return"redirect:"+Config.HOME_URL;

    }

    @GetMapping("${url.search}")
    public ResponseEntity<List<EmployeeDTO>> searchByQuery(@RequestParam(name="query",required = false)String query,@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5")int size,HttpSession session) {
        LOGGER.info("Accessed GET '/api/employees/search?query="+query+"&page="+page+"&size="+size+"'");

        try {
            ManagementService managementService = new ManagementService(employeesRepository);
            List<EmployeeDTO> searchedEmployees =managementService.findBySearchQuery(query,page,size);
            LOGGER.info("Search was successful ");
            return ResponseEntity.ok(searchedEmployees);

        } catch (Exception e) {
            LOGGER.severe("Caught an Exception: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("${url.average_salary}")
    public ResponseEntity<Double>generateAveSalary() {
        LOGGER.info("Accessed GET '/api/employees/average_salary'");

        try {
            GenerateAverageSalary generateAverageSalary= new GenerateAverageSalary(employeesRepository);
            double averageSalary =generateAverageSalary.generate();
            LOGGER.info("Average salary has been successfully computed");
            return ResponseEntity.ok(averageSalary);

        } catch (Exception e) {
            LOGGER.severe("Caught an Exception: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("${url.average_salary}/dept={department}")
    public ResponseEntity<Double>generateAveSalary(@PathVariable("department") String department) {
        LOGGER.info("Accessed GET '/api/employees/average_salary/dept="+department+"'");

        try {
            GenerateAverageSalary generateAverageSalary= new GenerateAverageSalary(employeesRepository);
            double averageSalaryDept= generateAverageSalary.generate(department);
            LOGGER.info("Average salary has been successfully computed");
            return ResponseEntity.ok(averageSalaryDept);

        } catch (Exception e) {
            LOGGER.severe("Caught an Exception: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("${url.average_age}")
    public ResponseEntity<Double>generateAveAge() {
        LOGGER.info("Accessed GET '/api/employees/average_age'");

        try {
            GenerateAverageAge generateAverageAge= new GenerateAverageAge(employeesRepository);
            double AverageAge= generateAverageAge.generate();
            LOGGER.info("Average age has been successfully computed");
            return ResponseEntity.ok(AverageAge);

        } catch (Exception e) {
            LOGGER.severe("Caught an Exception: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("${url.average_age}/dept={department}")
    public ResponseEntity<Double>generateAveAge(@PathVariable("department") String department) {
        LOGGER.info("Accessed GET '/api/employees/average_age/dept="+department+"'");

        try {
            GenerateAverageAge generateAverageAge= new GenerateAverageAge(employeesRepository);
            double AverageAge= generateAverageAge.generate(department);

            LOGGER.info("Average age has been successfully computed");
            return ResponseEntity.ok(AverageAge);

        } catch (Exception e) {
            LOGGER.severe("Caught an Exception: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("${url.filtered}")
    public ResponseEntity<List<EmployeeDTO>> filterEmployees(@RequestParam(name="department",required = false)String department,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "5")int size) {
        LOGGER.info("Accessed GET '/api/employees/filtered?department="+department+"&page="+page+"&size="+size+"'");
        try {
            ManagementService managementService = new ManagementService(employeesRepository);
            List<EmployeeDTO> filteredEmployees = managementService.getFilteredEmployees(department,page,size);
            LOGGER.info("Filtered employees successfully gathered");
            return ResponseEntity.ok(filteredEmployees);

        } catch (Exception e) {
            LOGGER.severe("Caught an Exception: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("${url.sorted}")
    public ResponseEntity<List<EmployeeDTO>> displaySortedEmployees(@RequestParam(name="sortBy")String sortBy,@RequestParam(name = "sortOrder",defaultValue = "ASC") String sortOrder) {
        LOGGER.info("Accessed GET '/api/employees/sorted?sortBy="+sortBy+"&sortOrder="+sortOrder+"'");

        try {
            ManagementService managementService = new ManagementService(employeesRepository);
            List<EmployeeDTO> sortedEmployees =managementService.getAllSortedEmployees(sortBy,sortOrder);
            LOGGER.info("Employees successfully sorted");
            return ResponseEntity.ok(sortedEmployees);

        } catch (Exception e) {
            LOGGER.severe("Caught an Exception: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


}
