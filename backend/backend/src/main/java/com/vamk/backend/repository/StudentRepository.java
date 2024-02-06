package com.vamk.backend.repository;

import com.vamk.backend.model.Student;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
// public interface StudentRepository extends CrudRepository<Student, UUID> {}
public class StudentRepository {

    public Iterable<Student> findAll() {
        return List.of();
    }

    public Optional<Student> findById(UUID uuid) {
        return Optional.empty();
    }
}
