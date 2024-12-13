package com.emp.employeeManagement.service;

import com.emp.employeeManagement.model.Contact;
import com.emp.employeeManagement.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Notification {

    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getContactCountByStatus() {
        return contactRepository.findByStatus(String.valueOf(1)); // Fetch count where status = 1
    }

    public List<Contact> getAllContact() {
        return contactRepository.findAll(Sort.by(Sort.Order.desc("createDate")));
    }

    public void updateContactStatus() {
        contactRepository.updateStatus("0");
    }
}
