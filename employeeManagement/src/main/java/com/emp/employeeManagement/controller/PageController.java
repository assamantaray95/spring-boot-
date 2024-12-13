package com.emp.employeeManagement.controller;

import com.emp.employeeManagement.model.Contact;
import com.emp.employeeManagement.model.ContactData;
import com.emp.employeeManagement.model.Employee;
import com.emp.employeeManagement.repository.EmployeeRepository;
import com.emp.employeeManagement.service.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PageController {

    @Autowired
    private EmployeeRepository empRepo;

    @Autowired
    private Notification notify;

    @GetMapping({"/" , "/home"})
    public String home(Model model) {
        List<Employee> activeEmployee = empRepo.findByStatusOrderByCreateDateDesc("1");
        model.addAttribute("activeEmployee", activeEmployee);
        // Set the page title dynamically for this page
        model.addAttribute("pageTitle", "Home");
        return "home"; // The name of the Thymeleaf template, e.g., somePage.html
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        ContactData contact = new ContactData();
        model.addAttribute("contactData", contact);
        // Set the page title dynamically for this page
        model.addAttribute("pageTitle", "Contact");
        return "contact"; // The name of the Thymeleaf template, e.g., somePage.html
    }

    @GetMapping("/about")
    public String about(Model model) {
        // Set the page title dynamically for this page
        model.addAttribute("pageTitle", "About");
        return "about"; // The name of the Thymeleaf template, e.g., somePage.html
    }

    @GetMapping("/admin/login")
    public String login(Model model) {
        // Set the page title dynamically for this page
        model.addAttribute("pageTitle", "Login");
        return "/admin/login"; // The name of the Thymeleaf template, e.g., somePage.html
    }

    @GetMapping("/admin/index")
    public String adminDashboard(Model model) {
        List<Employee> lastFiveEmployees = empRepo.findTop4ByStatusOrderByCreateDateDesc("1");
        model.addAttribute("lastFiveEmployees", lastFiveEmployees);

        List<Employee> employeesWithStatusOne = empRepo.findByStatusOrderByCreateDateDesc("1");
        // Prepare arrays for dates and counts
        List<String> dates = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();

        // Use SimpleDateFormat to format dates into yyyy-MM-dd format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Group employees by date (count employees per date)
        Map<String, Long> employeeCountByDate = employeesWithStatusOne.stream()
                .collect(Collectors.groupingBy(e -> dateFormat.format(e.getCreateDate()), Collectors.counting()));

        // Populate dates and counts arrays
        for (Map.Entry<String, Long> entry : employeeCountByDate.entrySet()) {
            dates.add(entry.getKey());  // Add date in "yyyy-MM-dd" format
            counts.add(entry.getValue().intValue());  // Add the count
        }

        // Add data to model
        model.addAttribute("dates", dates);
        model.addAttribute("counts", counts);

        List<Contact> count = notify.getContactCountByStatus();
        model.addAttribute("notificationCount", count.size());

        // Set the page title dynamically for this page
        model.addAttribute("pageTitle", "Dashboard");
        return "/admin/index"; // The name of the Thymeleaf template, e.g., somePage.html
    }

    @GetMapping("/admin/employee-list")
    public String empList(Model model) {
        // Fetch all employees from the database
        List<Employee> employees = empRepo.findAll(Sort.by(Sort.Order.desc("id")));

        model.addAttribute("employees", employees);

        List<Contact> count = notify.getContactCountByStatus();
        model.addAttribute("notificationCount", count.size());
        // Set the page title dynamically for this page
        model.addAttribute("pageTitle", "Emp List");
        return "/admin/employee-list"; // The name of the Thymeleaf template, e.g., somePage.html
    }

    @GetMapping("/admin/add-employee/{id}")
    public String editEmployee(@PathVariable("id") Long id, Model model) {
        List<Contact> count = notify.getContactCountByStatus();
        model.addAttribute("notificationCount", count.size());
        // Fetch the employee by ID (you can add error handling if not found)
        Optional<Employee> employeeOptional = empRepo.findById(id);
        employeeOptional.ifPresent(employee -> model.addAttribute("empData", employee));
        model.addAttribute("pageTitle", "Edit Employee");
        return "/admin/add-employee"; // This returns the 'add-employee' page for editing
    }

    @GetMapping("/admin/notification")
    public String notification(Model model) {
        List<Contact> count = notify.getContactCountByStatus();
        model.addAttribute("notificationCount", count.size());
        if(count.size() > 0) {
            notify.updateContactStatus();
        }
        List<Contact> contacts = notify.getAllContact();
        model.addAttribute("contactDetails", contacts);
        model.addAttribute("pageTitle", "Notification");
        return "/admin/notification"; // This returns the 'add-employee' page for editing
    }
}
