package com.emp.employeeManagement.controller;

import com.emp.employeeManagement.model.Contact;
import com.emp.employeeManagement.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class PublicController {

    @Autowired
    private ContactRepository conRepo;

    @PostMapping("/contact")
    public String addContact(@Valid @ModelAttribute("contactData") Contact empContact, BindingResult result, Model model) {
        System.out.println("validity" + result.hasErrors());
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Contact");
            return "/contact"; // Return to the add-employee form
        }
        empContact.setConStatus("1");
        empContact.setCreatedAt(new Date());
        conRepo.save(empContact);
        return "redirect:/contact";
    }
}
