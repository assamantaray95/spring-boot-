package com.emp.employeeManagement.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static PasswordEncoder passwordEncoder;

    // Define the PasswordEncoder bean
    // Constructor injection of PasswordEncoder
    public UserService(PasswordEncoder passwordEncoder) {
        UserService.passwordEncoder = passwordEncoder;
    }

    // Create users and return the UserDetailsService
    public static UserDetailsService getUserDetailsService() {
        UserDetails user1 = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("cms")) // Encoding password
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1);
    }
}
