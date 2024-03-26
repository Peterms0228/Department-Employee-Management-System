package com.example.peterassignment.specificaiton;

import com.example.peterassignment.entity.Department;
import com.example.peterassignment.entity.Employee;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class DepartmentSpecification {
    public static Specification<Department> findAll(){
        return (root, query, criteriaBuilder) -> {
            query.where(criteriaBuilder.equal(root.get("status"), "Y"));
            return null;
        };
    }
    public static Specification<Department> findAll(String sortBy, String searchType, String search) {
        return (root, query, criteriaBuilder) -> {
            query.where(criteriaBuilder.equal(root.get("status"), "Y"));
            if(searchType != null && search != null && search.length() > 0){
                if(searchType.equals("name") || searchType.equals("location")){
                    query.where(criteriaBuilder.like(root.get(searchType), "%" + search + "%"));
                } else{
                    query.where(criteriaBuilder.equal(root.get(searchType), search));
                }
            }
            if(sortBy != null){
                query.orderBy(criteriaBuilder.asc(root.get(sortBy)));
            }
            return null;
        };
    }
}
