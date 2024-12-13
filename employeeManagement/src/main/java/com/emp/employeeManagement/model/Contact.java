package com.emp.employeeManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Email is required")
    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @NotEmpty(message = "Subject is required")
    private String subject;
    private String message;


    private String status;

    private Date createDate;

    public void setCreatedAt(Date date) {
        this.createDate = date;
    }

    public void setConStatus(String number) {
        this.status = number;
    }
}

