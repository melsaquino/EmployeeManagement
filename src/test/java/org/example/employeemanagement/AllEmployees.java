package org.example.employeemanagement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc
@SpringBootTest
public class AllEmployees extends LoginTest {
    @Autowired
    private MockMvc mockMvc;
    private int employees=0;
    /**
     * Tests that api/employee returns a json and the fields displayed are complete
     * */
    @Test
    public void testGetEmployee() throws Exception{
        // Perform the GET request and get the result
        MvcResult result = mockMvc.perform(get("/api/employees").session(this.session))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonResponse);
        Assertions.assertTrue(jsonNode.isArray(), "Expected JSON array");
        for (JsonNode employeeNode : jsonNode) {
            employees++;
            Assertions.assertTrue(employeeNode.has("employeeId"), "JSON should contain 'employeeId'");
            Assertions.assertTrue(employeeNode.has("name"), "JSON should contain 'name'");
            Assertions.assertTrue(employeeNode.has("department"), "JSON should contain 'department'");
            Assertions.assertTrue(employeeNode.has("salary"), "JSON should contain 'salary'");
            Assertions.assertTrue(employeeNode.has("dateOfBirth"), "JSON should contain 'dateOfBirth'");
        }
    }
    /**
     * Tests that the filter function works and that the json has the correct value based on the department being filtered
     * */
    @Test
    public void testFilteredDepartment() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/employees/filtered?department=IT").session(this.session))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonResponse);
        Assertions.assertTrue(jsonNode.isArray(), "Expected JSON array");
        Assertions.assertTrue(jsonNode.size() <=  employees);

        for (JsonNode employeeNode : jsonNode) {
            Assertions.assertTrue(employeeNode.has("employeeId"), "JSON should contain 'employeeId'");
            Assertions.assertTrue(employeeNode.has("name"), "JSON should contain 'name'");
            Assertions.assertTrue(employeeNode.has("department"), "JSON should contain 'department'");
            Assertions.assertTrue(employeeNode.has("salary"), "JSON should contain 'salary'");
            Assertions.assertTrue(employeeNode.has("dateOfBirth"), "JSON should contain 'dateOfBirth'");
            assertEquals("it",employeeNode.get("department").asText().toLowerCase());
        }

    }
    /**
     * Tests that/api/employees/average_salary endpoint will return a value that can be parsed into a double or a valid average value
     * */
    @Test
    public void testGeneratesAverageSalary() throws Exception{
        MvcResult result = mockMvc.perform(get("/api/employees/average_salary").session(this.session))
                .andExpect(status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();

        assertDoesNotThrow(() -> Double.parseDouble(response));
    }
    /**
     * Tests that/api/employees/average_age endpoint will return a value that can be parsed into a double or a valid average value
     * */
    @Test
    public void testGeneratesAverageAge() throws Exception{
        MvcResult result = mockMvc.perform(get("/api/employees/average_age").session(this.session))
                .andExpect(status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();

        assertDoesNotThrow(() -> Double.parseDouble(response));
    }
}
