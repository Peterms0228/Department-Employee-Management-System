package com.example.peterassignment.controller;

import com.example.peterassignment.entity.Department;
import com.example.peterassignment.service.DepartmentService;
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
@RequestMapping("department")
public class DepartmentController {
    private DepartmentService departmentService;

    //------<start>- REST Controller -<start>------
    // build create Department REST API
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department){
        Department savedDepartment = departmentService.createDepartment(department);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    // build get Department by id REST API
    // http://localhost:8080/department/1
    @GetMapping("{dept_id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("dept_id") Long id){
        Department department = departmentService.getDepartmentById(id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    // Build Get All Departments REST API
    // http://localhost:8080/department
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments(){
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    // Build Update Department REST API
    // http://localhost:8080/department/1
    @PutMapping("{dept_id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable("dept_id") Long id,
                                                       @RequestBody Department department){
        department.setId(id);
        Department updatedDepartment = departmentService.updateDepartment(department);
        return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
    }

    // Build Delete Department REST API
    @DeleteMapping("{dept_id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("dept_id") Long id){
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>("Department successfully deleted!", HttpStatus.OK);
    }
    //------<end>- REST Controller -<end>------



    // Get List with Search Elements
    @GetMapping(value = "/list")
    public String departmentList(@RequestParam(value = "searchType", required = false) String searchType,
                                 @RequestParam(value = "search", required = false) String search,
                                 @RequestParam(value = "page", defaultValue = "0") Integer page,
                                 @RequestParam(value = "sortType", required = false) String sortType,
                                 Model model){
        int pageSize = 5;
        List<Department> departmentList = departmentService.getAllDepartments(sortType, searchType, search);
        Page<Department> departmentPage = departmentService.getAllDepartmentsByPage(departmentList, page, pageSize);

        model.addAttribute("searchType", searchType);
        model.addAttribute("search", search);
        model.addAttribute("totalRows", departmentPage.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("departmentPage", departmentPage);
        return "department_list";
    }

    //Load "Add"'s Page
    @GetMapping(value = "/add")
    public String departmentAdd(Model model){
        model.addAttribute("department", new Department());
        return "department_add";
    }

    //Submit "Add"'s Form
    @PostMapping(value = "/add/submit")
    public String departmentAddSubmit(Department department){
        department.setStatus("Y");
        departmentService.createDepartment(department);
        return "redirect:/department/list";
    }

    //Load "Update"'s Page
    @GetMapping(value = "/update/{id}")
    public String departmentUpdate(@PathVariable Long id, Model model){
        model.addAttribute("department", departmentService.getDepartmentById(id));
        return "department_update";
    }

    //Submit "Update"'s Form
    @PostMapping(value = "/update/{id}/submit")
    public String departmentUpdateSubmit(@PathVariable("id") Long id,
                                         Department updatedDepartment){
        updatedDepartment.setId(id);
        departmentService.updateDepartment(updatedDepartment);
        return "redirect:/department/list";
    }

    //Delete Selected Item
    @PostMapping(value = "/delete/{id}")
    public String departmentDelete(@PathVariable("id") Long id){
        Department department = departmentService.getDepartmentById(id);
        department.setStatus("N");
        if(department.getEmployees().size() > 0){
            for(int i = 0; i < department.getEmployees().size(); i++){
                department.getEmployees().get(i).setDepartment(null);
            }
        }

        departmentService.updateDepartment(department);
        return "redirect:/department/list";
    }

}
