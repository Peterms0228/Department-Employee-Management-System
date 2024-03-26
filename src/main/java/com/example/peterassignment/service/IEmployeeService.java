package com.example.peterassignment.service;

import com.example.peterassignment.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEmployeeService {
    Employee createEmployee(Employee employee);
    Employee getEmployeeById(Long id);
    List<Employee> getAllEmployees();
    List<Employee> getAllEmployees(String sortBy, String searchType, String search);
    Page<Employee> getAllEmployeesByPage(List<Employee> employeeList, int page, int size);
    Employee updateEmployee(Employee employee);
    void deleteEmployee(Long id);
}
