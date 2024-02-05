package com.vamk.backend.controller;

import com.vamk.backend.model.Course;
import com.vamk.backend.model.Student;
import com.vamk.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
public class StudentController {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("api/students")
    public Set<Student> getStudents() {
        return Set.of();
    }

    @GetMapping("/api/students/{id}")
    public Student getStudent(@PathVariable String id) {
        return new Student(UUID.randomUUID(), "test@test.com", "John", "Doe");
    }

    @PostMapping("/api/students/{id}")
    public void updateStudent(
            @PathVariable String id,
            @RequestBody String emailAddress,
            @RequestBody String firstName,
            @RequestBody String lastName
    ) {

    }

    @GetMapping("/api/course/{id}")
    public Course getCourse(@PathVariable String id) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/api/course/{id}")
    public void updateCourse(@PathVariable String id, @RequestBody String name, @RequestBody String teacher) {

    }
}
