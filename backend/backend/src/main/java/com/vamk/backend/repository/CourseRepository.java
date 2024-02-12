package com.vamk.backend.repository;

import com.vamk.backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, Long> {}
