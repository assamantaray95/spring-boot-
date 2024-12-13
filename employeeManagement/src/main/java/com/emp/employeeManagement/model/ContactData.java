package com.emp.employeeManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ContactData {

    private String name;

    private String email;


    private String subject;

    private String message;

    private String status;

    private Date createDate;
}
