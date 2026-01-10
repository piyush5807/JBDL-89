package com.example.demo_db.controllers;

import com.example.demo_db.dtos.CreateEmployeeRequest;
import com.example.demo_db.dtos.GetEmployeeResponse;
import com.example.demo_db.models.Employee;
import com.example.demo_db.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/employee")
    public void createEmployee(@RequestBody CreateEmployeeRequest createEmployeeRequest) throws SQLException {
        employeeService.createEmployee(createEmployeeRequest);
    }

    @GetMapping("/employee/{employeeId}")
    public Employee getEmployee(@PathVariable("employeeId") Integer employeeId) throws SQLException {
        return this.employeeService.getEmployee(employeeId);
    }

    @GetMapping("/employees-all")
    public List<Employee> getAllEmployees() throws SQLException {
        return this.employeeService.getEmployees();
    }

    @PatchMapping("/employee/{employeeId}")
    public void updateEmployee(@PathVariable("employeeId") Integer employeeId,
                               @RequestBody CreateEmployeeRequest createEmployeeRequest) throws SQLException {
    }

    @DeleteMapping("/employee/{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") Integer employeeId) throws SQLException {

    }

}
