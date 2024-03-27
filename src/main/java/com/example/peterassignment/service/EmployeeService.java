package com.example.peterassignment.service;

import com.example.peterassignment.entity.Employee;
import com.example.peterassignment.repository.IEmployeeRepository;
import com.example.peterassignment.specificaiton.EmployeeSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService implements IEmployeeService{

    private IEmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.get();
    }
    public List<Employee> getAllEmployeesTest() {
        return employeeRepository.findAll();
    }
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll(EmployeeSpecification.findAll());
    }
    public List<Employee> getAllEmployees(String sortBy, String searchType, String search){
        return employeeRepository.findAll(EmployeeSpecification.findAll(sortBy, searchType, search));
    }

    public Page<Employee> getAllEmployeesByPage(List<Employee> employeeList, int page, int size){
        Pageable pageRequest = PageRequest.of(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), employeeList.size());
        List<Employee> pageContent = employeeList.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, employeeList.size());
    }
    public Employee updateEmployee(Employee employee) {
        Employee existingEmployee = employeeRepository.findById(employee.getId()).get();
        existingEmployee.setName(employee.getName());
        existingEmployee.setPhoneNo(employee.getPhoneNo());
        existingEmployee.setDepartment(employee.getDepartment());
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return updatedEmployee;
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
