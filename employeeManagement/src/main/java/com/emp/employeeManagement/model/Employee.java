package com.emp.employeeManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "First Name is required")
    private String firstName;
    @NotEmpty(message = "Last Name is required")
    private String lastName;
    @NotEmpty(message = "Designation is required")
    private String designation;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    private String email;

    @NotEmpty(message = "Image is required")
    private String image;

    private String phone;

    private String address;

    @NotEmpty(message = "Status is required")
    private String status;


    private Date createDate;

    public void setCreatedAt(Date date) {
        this.createDate = date;
    }

//    public void setImagePath(String absolutePath) {
//        this.image = absolutePath;
//    }
}
