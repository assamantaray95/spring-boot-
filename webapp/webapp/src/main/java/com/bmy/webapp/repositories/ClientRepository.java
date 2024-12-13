package com.bmy.webapp.repositories;

import com.bmy.webapp.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    public Client findByEmail(String email);
}
