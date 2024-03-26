package com.example.peterassignment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="department")
public class Department {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="dept_id")
    private long id;

    @Column(name="dept_name")
    private String name;

    @Column(name="dept_location")
    private String location;

    @Column(name="dept_status")
    private String status;

    @OneToMany
    @JoinColumn(name="emp_dept_id")
    private List<Employee> employees;

    public String toString(){
        return id + ", " + name + ", " + location;
    }

}
