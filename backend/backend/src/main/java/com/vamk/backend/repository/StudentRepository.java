package com.vamk.backend.repository;

import com.vamk.backend.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface StudentRepository extends CrudRepository<Student, UUID> {}
