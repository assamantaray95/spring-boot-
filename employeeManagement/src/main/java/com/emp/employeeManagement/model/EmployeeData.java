package com.emp.employeeManagement.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class EmployeeData {


    private String firstName;


    private String lastName;


    private String designation;


    private String email;


    private String image;


    private String phone;


    private String address;


    private String status;


    private Date createDate;
    // Lombok will generate the getters and setters for these fields automatically
    // No need to manually define them here
}
