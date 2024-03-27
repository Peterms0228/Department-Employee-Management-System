package com.example.peterassignment.specificaiton;

import com.example.peterassignment.entity.Department;
import com.example.peterassignment.entity.Employee;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {
    public static Specification<Employee> findAll() {
        return (root, query, criteriaBuilder) -> {
            query.where(criteriaBuilder.equal(root.get("status"), "Y"));
            return null;
        };
    }
    public static Specification<Employee> findAll(String sortBy, String searchType, String search) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Employee, Department> departmentJoin = root.join("department", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(root.get("status"), "Y"));
            if(searchType != null && searchType.length() > 0){
                if(search != null && search.length() > 0) {
                    if(searchType.equals("id")){
                        try{
                            predicates.add(criteriaBuilder.equal(root.get(searchType), search));
                        }catch (Exception e){
                            predicates.add(criteriaBuilder.equal(root.get(searchType), 0));
                        }
                    } else if (searchType.equals("department")) {
                        if(search.equals("null")){
                            predicates.add(criteriaBuilder.isNull(root.get("department")));
                        }
                        predicates.add(criteriaBuilder.like(departmentJoin.get("name"), "%" + search + "%"));
                    }
                } else{
                    predicates.add(criteriaBuilder.like(root.get(searchType), "%" + search + "%"));
                }
            }

            Predicate[] predicatesArray = new Predicate[predicates.size()];
            predicatesArray = predicates.toArray(predicatesArray);
            query.where(predicatesArray);

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
