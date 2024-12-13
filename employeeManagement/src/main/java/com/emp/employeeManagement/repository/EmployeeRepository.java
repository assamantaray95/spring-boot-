package com.emp.employeeManagement.repository;

import com.emp.employeeManagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public Employee findByEmail(String email);
    List<Employee> findTop4ByStatusOrderByCreateDateDesc(String status);
    List<Employee> findByStatusOrderByCreateDateDesc(String status);
}
