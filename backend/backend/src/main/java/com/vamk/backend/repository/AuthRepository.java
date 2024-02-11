package com.vamk.backend.repository;

import com.vamk.backend.model.AuthEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthEntry, Long> {}
