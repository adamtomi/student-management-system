package com.vamk.backend.repository;

import com.vamk.backend.model.AuthEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<AuthEntry, Long> {

    Optional<AuthEntry> findByEmail(String email);
}
