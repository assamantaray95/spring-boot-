package com.emp.employeeManagement.security;


import com.emp.employeeManagement.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        // Provide a BCryptPasswordEncoder bean
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/",
                                "/home",
                                "/css/**",
                                "/images/**" ,
                                "/js",
                                "/contact",
                                "/about",
                                "/emp-details"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/admin/login")
                        .permitAll()
                        .defaultSuccessUrl("/admin/index", true)
                )
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        // Use the UserService to get the UserDetailsService
        return UserService.getUserDetailsService();
    }

}
