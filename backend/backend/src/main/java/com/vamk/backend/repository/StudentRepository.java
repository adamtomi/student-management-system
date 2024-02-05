package com.vamk.backend.repository;

import com.vamk.backend.model.Student;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
public class StudentRepository {

    public CompletableFuture<Set<Student>> getAll() {
        throw new UnsupportedOperationException();
    }

    public CompletableFuture<Student> findById(UUID uniqueId) {
        throw new UnsupportedOperationException();
    }


}
