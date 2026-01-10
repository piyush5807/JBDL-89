package com.example.demo_db.services;

import com.example.demo_db.dtos.CreateEmployeeRequest;
import com.example.demo_db.models.Employee;
import com.example.demo_db.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service // @Component
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public void createEmployee(CreateEmployeeRequest createEmployeeRequest) throws SQLException {
        this.employeeRepository.createEmployee(createEmployeeRequest);
    }

    public Employee getEmployee(Integer id) throws SQLException {
        return this.employeeRepository.getEmployeeById(id);
    }

    public List<Employee> getEmployees() throws SQLException {
        return this.employeeRepository.getAllEmployees();
    }
}
