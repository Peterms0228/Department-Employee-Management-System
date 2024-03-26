package com.example.peterassignment.specificaiton;

import com.example.peterassignment.entity.Department;
import com.example.peterassignment.entity.Employee;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {
    public static Specification<Employee> findAll() {
        return (root, query, criteriaBuilder) -> {
            query.where(criteriaBuilder.equal(root.get("status"), "Y"));
            return null;
        };
    }
    public static Specification<Employee> findAll(String sortBy, String searchType, String search) {
        return (root, query, criteriaBuilder) -> {
            Join<Employee, Department> departmentJoin = root.join("dept", JoinType.INNER);

            query.where(criteriaBuilder.equal(root.get("status"), "Y"));
            if(searchType != null && search != null && search.length() > 0){
                if(searchType.equals("name") || searchType.equals("phoneNo")){
                    query.where(criteriaBuilder.like(root.get(searchType), "%" + search + "%"));
                } else if (searchType.equals("dept")) {
                    query.where(criteriaBuilder.like(departmentJoin.get("name"), "%" + search + "%"));
                } else{
                    query.where(criteriaBuilder.equal(root.get(searchType), search));
                }
            }
            if(sortBy != null){
                if(sortBy.equals("dept")){
                    query.orderBy(criteriaBuilder.asc(departmentJoin.get("name")));
                }
                query.orderBy(criteriaBuilder.asc(root.get(sortBy)));
            }
            return null;
        };
    }
}
