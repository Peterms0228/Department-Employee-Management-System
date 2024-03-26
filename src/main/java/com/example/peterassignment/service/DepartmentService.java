package com.example.peterassignment.service;

import com.example.peterassignment.entity.Department;
import com.example.peterassignment.repository.IDepartmentRepository;
import com.example.peterassignment.specificaiton.DepartmentSpecification;
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
public class DepartmentService implements IDepartmentService{

    private IDepartmentRepository departmentRepository;

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department getDepartmentById(Long id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.get();
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll(DepartmentSpecification.findAll());
    }
    public List<Department> getAllDepartments(String sortBy, String searchType, String search) {
        return departmentRepository.findAll(DepartmentSpecification.findAll(sortBy, searchType, search));
    }

    public Page<Department> getAllDepartmentsByPage(List<Department> departmentList, int page, int size){
        Pageable pageRequest = PageRequest.of(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), departmentList.size());
        List<Department> pageContent = departmentList.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, departmentList.size());
    }

    public Department updateDepartment(Department department) {
        Department existingDepartment = departmentRepository.findById(department.getId()).get();
        existingDepartment.setName(department.getName());
        existingDepartment.setLocation(department.getLocation());
        Department updatedDepartment = departmentRepository.save(existingDepartment);
        return updatedDepartment;
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
