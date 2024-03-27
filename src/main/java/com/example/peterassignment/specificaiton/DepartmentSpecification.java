package com.example.peterassignment.specificaiton;

import com.example.peterassignment.entity.Department;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DepartmentSpecification {
    public static Specification<Department> findAll(){
        return (root, query, criteriaBuilder) -> {
            query.where(criteriaBuilder.equal(root.get("status"), "Y"));
            return null;
        };
    }
    public static Specification<Department> findAll(String sortBy, String searchType, String search) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("status"), "Y"));
            if(searchType != null && searchType.length() > 0) {
                if(search != null && search.length() > 0){
                    if(searchType.equals("id")){
                        try{
                            predicates.add(criteriaBuilder.equal(root.get(searchType), search));
                        }catch (Exception e){
                            predicates.add(criteriaBuilder.equal(root.get(searchType), 0));
                        }
                    }
                }else{
                    predicates.add(criteriaBuilder.like(root.get(searchType), "%" + search + "%"));
                }
            }

            Predicate[] predicatesArray = new Predicate[predicates.size()];
            predicatesArray = predicates.toArray(predicatesArray);
            query.where(predicatesArray);

            if(sortBy != null){
                query.orderBy(criteriaBuilder.asc(root.get(sortBy)));
            }
            return null;
        };
    }
}
