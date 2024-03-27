package com.example.peterassignment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="emp_id")
    private long id;

    @Column(name="emp_name")
    private String name;

    @Column(name="emp_phone_no")
    private String phoneNo;

    @Column(name="emp_status", length = 1)
    private String status;

    @ManyToOne
    @JoinColumn(name = "emp_dept_id")
    private Department department;

    public String toString(){
        return id + ", " + name + ", " + phoneNo;
    }

}
