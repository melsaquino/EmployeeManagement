package org.example.employeemanagement.Services;

import jakarta.transaction.Transactional;
import org.example.employeemanagement.Entities.Employee;
import org.example.employeemanagement.Repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
/** Class that handles the login and implements UserDetailsService for authentication purposes*/
public class LoginService implements UserDetailsService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Autowired
    private EmployeesRepository employeesRepository;

    /** Implementation of loadUserName method of UserDetailsService
     * Finds a user in the user database where the credentials match*/
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Employee user = employeesRepository.findByEmployeeId(Integer.parseInt(id));
        if (user==null)
            throw new UsernameNotFoundException("No user found!");
        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getEmployeeId()),
                user.getPassword(), new ArrayList<>());
    }

}
