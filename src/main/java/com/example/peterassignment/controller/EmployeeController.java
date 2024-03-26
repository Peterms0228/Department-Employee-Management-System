package com.example.peterassignment.controller;

import com.example.peterassignment.entity.Department;
import com.example.peterassignment.entity.Employee;
import com.example.peterassignment.service.DepartmentService;
import com.example.peterassignment.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@AllArgsConstructor
@RequestMapping("employee")
public class EmployeeController {
    private EmployeeService employeeService;
    private DepartmentService departmentService;

    //------<start>- REST Controller -<start>------
    // build create Employee REST API
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        Employee savedEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // build get Employee by id REST API
    // http://localhost:8080/employee/1
    @GetMapping("{emp_id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("emp_id") Long id){
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    // build get Employee by id REST API
    // http://localhost:8080/employee
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // Build Update Employee REST API
    // http://localhost:8080/employee/1
    @PutMapping("{emp_id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("emp_id") Long id,
                                                   @RequestBody Employee employee){
        employee.setId(id);
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    //Build Delete Employee REST API
    @DeleteMapping("{emp_id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("emp_id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee successfully deleted!", HttpStatus.OK);
    }
    //------<end>- REST Controller -<end>------



    // Get List with Search Elements
    @GetMapping(value = "/list")
    public String employeeList(@RequestParam(value = "searchType", required = false) String searchType,
                               @RequestParam(value = "search", required = false) String search,
                               @RequestParam(value = "page", defaultValue = "0") Integer page,
                               @RequestParam(value = "sortType", required = false) String sortType,
                               Model model){
        int pageSize = 5;
        List<Employee> employeeList = employeeService.getAllEmployees(sortType, searchType, search);
        Page<Employee> employeePage = employeeService.getAllEmployeesByPage(employeeList, page, pageSize);

        model.addAttribute("searchType", searchType);
        model.addAttribute("search", search);
        model.addAttribute("totalRows", employeePage.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("employeePage", employeePage);
        return "employee_list";
    }

    //Load "Add"'s Page
    @GetMapping(value = "/add")
    public String employeeAdd(Model model){
        model.addAttribute("employee", new Employee());
        List<Department> departmentList = departmentService.getAllDepartments();
        model.addAttribute("departmentList", departmentList);
        return "employee_add";
    }

    //Submit "Add"'s Form
    @PostMapping(value = "/add/submit")
    public String employeeAddSubmit(Employee employee){
        employee.setStatus("Y");
        employeeService.createEmployee(employee);
        return "redirect:/employee/list";
    }

    //Load "Update"'s Page
    @GetMapping(value = "/update/{id}")
    public String employeeUpdate(@PathVariable Long id, Model model){
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        List<Department> departmentList = departmentService.getAllDepartments();
        model.addAttribute("departmentList", departmentList);
        return "employee_update";
    }

    //Submit "Update"'s Form
    @PostMapping(value = "/update/{id}/submit")
    public String employeeUpdateSubmit(@PathVariable("id") Long id,
                                       Employee updatedEmployee){
        updatedEmployee.setId(id);
        employeeService.updateEmployee(updatedEmployee);
        return "redirect:/employee/list";
    }

    //Delete Selected Item
    @PostMapping(value = "/delete/{id}")
    public String employeeDelete(@PathVariable("id") Long id){
        Employee employee = employeeService.getEmployeeById(id);
        employee.setStatus("N");
        employeeService.updateEmployee(employee);
        return "redirect:/employee/list";
    }


}
