package com.emp.employeeManagement.repository;

import com.emp.employeeManagement.model.Contact;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByStatus(String status);

    @Modifying
    @Transactional
    @Query("UPDATE Contact c SET c.status = '0' WHERE c.status = '1'")
    void updateStatus(@Param("status") String status);  // Make sure the parameter is an int
    
}
