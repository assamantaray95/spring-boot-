package com.emp.employeeManagement.controller;

import com.emp.employeeManagement.model.Contact;
import com.emp.employeeManagement.model.Employee;
import com.emp.employeeManagement.model.EmployeeData;
import com.emp.employeeManagement.repository.EmployeeRepository;
import com.emp.employeeManagement.service.Notification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class EmployeeController {

    @Autowired
    private EmployeeRepository empRepo;

    @Autowired
    private Notification notify;


    @GetMapping("/add-employee")
    public String addClient(Model model) {
        EmployeeData empData = new EmployeeData();
        model.addAttribute("pageTitle", "Add Emp");
        model.addAttribute("empData", empData);
        List<Contact> count = notify.getContactCountByStatus();
        model.addAttribute("notificationCount", count.size());
        return "/admin/add-employee";
    }

    @PostMapping("/add-employee")
    public String addEmployee(@Valid @ModelAttribute("empData") Employee empData, BindingResult result, Model model) {

        // If there are validation errors, return to the form with the error messages
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add Employee");
            return "/admin/add-employee"; // Return to the add-employee form
        }

        // Check if the email is already used by another employee
        if (empRepo.findByEmail(empData.getEmail()) != null) {
            result.addError(new FieldError("empData", "email", empData.getEmail(), false, null, null, "Email Address is already used"));
        }



        empData.setCreatedAt(new Date());

        // Save the employee data if there are no errors
        empRepo.save(empData);

        // Redirect to the employee list after successfully adding the employee
        return "redirect:/admin/employee-list";
    }

    @PostMapping("/add-employee/{id}")
    public String updateEmployee(@PathVariable(value = "id", required = false) Long id,
                               @Valid @ModelAttribute("empData") Employee empData,
                               BindingResult result, Model model) {

        // If the email is already used by another employee
        if (empRepo.findByEmail(empData.getEmail()) != null &&
                (id == null || !empData.getEmail().equals(empRepo.findById(id).get().getEmail()))) {
            result.addError(new FieldError("empData", "email", empData.getEmail(), false, null, null, "Email Address is already used"));
        }

        // If there are validation errors, return to the form
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", (id != null) ? "Edit Employee" : "Add Employee");
            return "/admin/add-employee";  // Return to the same page if there are errors
        }

        // If ID is provided, update the existing employee
        if (id != null) {
            Optional<Employee> existingEmployeeOptional = empRepo.findById(id);
            if (existingEmployeeOptional.isPresent()) {
                Employee existingEmployee = existingEmployeeOptional.get();
                existingEmployee.setFirstName(empData.getFirstName());
                existingEmployee.setLastName(empData.getLastName());
                existingEmployee.setDesignation(empData.getDesignation());
                existingEmployee.setEmail(empData.getEmail());
                existingEmployee.setPhone(empData.getPhone());
                existingEmployee.setAddress(empData.getAddress());
                existingEmployee.setStatus(empData.getStatus());
                existingEmployee.setImage(empData.getImage());
                // Don't modify 'id' or 'createdAt'
                empRepo.save(existingEmployee);
            } else {
                model.addAttribute("errorMessage", "Employee not found.");
                return "error-page";  // Or redirect to an error page
            }
        } else {
            // If no ID is provided, it's a new employee (adding)
            empData.setCreatedAt(new Date()); // Set created date for new employee
            empRepo.save(empData);
        }

        // Redirect to the employee list after saving
        return "redirect:/admin/employee-list";
    }

    @GetMapping("/employee-list/{id}")
    public String deleteEmployee(@PathVariable("id") Long id, Model model) {
        // Check if employee exists
        Optional<Employee> employeeOptional = empRepo.findById(id);

        if (employeeOptional.isPresent()) {
            // Delete employee from the database
            empRepo.deleteById(id);

            // Optionally, you can add a success message to the model
            model.addAttribute("successMessage", "Employee deleted successfully.");
        } else {
            // If employee doesn't exist
            model.addAttribute("errorMessage", "Employee not found.");
        }

        // Redirect to the employee list page after deletion
        return "redirect:/admin/employee-list";
    }
}
