package com.example.peterassignment.service;

import com.example.peterassignment.entity.Department;
import com.example.peterassignment.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDepartmentService {
    Department createDepartment(Department department);
    Department getDepartmentById(Long id);
    List<Department> getAllDepartments();
    List<Department> getAllDepartments(String sortBy, String searchType, String search);
    Page<Department> getAllDepartmentsByPage(List<Department> departmentList, int page, int size);
    Department updateDepartment(Department department);
    void deleteDepartment(Long id);
}
