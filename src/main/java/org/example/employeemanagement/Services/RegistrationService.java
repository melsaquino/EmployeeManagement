package org.example.employeemanagement.Services;


import org.example.employeemanagement.Entities.Admin;
import org.example.employeemanagement.Entities.Employee;
import org.example.employeemanagement.Repositories.AdminsRepository;
import org.example.employeemanagement.Repositories.EmployeesRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Service class deals with the functionalities of registering a new user
 * */
@Service
public class RegistrationService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final AdminsRepository adminsRepository;
    public RegistrationService(AdminsRepository adminsRepository){
        this.adminsRepository=adminsRepository;
    }
    /**
     * Method used to add the user to the users table of the database
     *
     * @param password the user's chosen password
     * @param psw_repeat the user's input for the re-submit password making sure the user knows the password they are making
     * */
    public void registerUser(int id, String name, LocalDate dateOfBirth, String department, double salary,
                             String password, String psw_repeat) throws Exception{
        if (!isPasswordsMatch(password,psw_repeat))
            throw new Exception("Passwords don't match. Re-enter passwords");
        if(adminsRepository.findByEmployeeId(id)==null){
            String hashedPassword = encoder.encode(password);
            Admin employee=new Admin();
            employee.setName(name);
            employee.setDateOfBirth(dateOfBirth);
            employee.setDepartment(department);
            employee.setSalary(salary);
            employee.setPassword(hashedPassword);
            employee.setEmployeeId(id);

            this.adminsRepository.save(employee);
        }
        else throw new Exception("User already exists!");
    }


    /**
     * Method checks if the two passwords entered by the user matches
     * */
    private boolean isPasswordsMatch(String password, String psw_repeat){
        return password.equals(psw_repeat);
    }

}
