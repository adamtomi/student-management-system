package com.vamk.backend.controller;

import com.vamk.backend.model.Course;
import com.vamk.backend.model.Student;
import com.vamk.backend.repository.StudentRepository;
import com.vamk.backend.util.Response;
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

    @GetMapping("/api/students")
    public Response getStudents() {
        return Response.success(200).withData(Set.of()).build();
    }

    @GetMapping("/api/students/{id}")
    public Response getStudent(@PathVariable String id) {
        Student dummy = new Student(UUID.randomUUID(), "test@test.com", "John", "Doe");
        return Response.success(200).withData(dummy).build();
    }

    @PostMapping("/api/students/{id}")
    public Response updateStudent(
            @PathVariable String id,
            @RequestBody String emailAddress,
            @RequestBody String firstName,
            @RequestBody String lastName
    ) {

        return Response.success(200).build();
    }

    @GetMapping("/api/course/{id}")
    public Response getCourse(@PathVariable String id) {
        Course dummy = new Course(UUID.randomUUID(), "Test", "John Doe");
        return Response.success(200).withData(dummy).build();
    }

    @PostMapping("/api/course/{id}")
    public Response updateCourse(@PathVariable String id, @RequestBody String name, @RequestBody String teacher) {
        return Response.success(200).build();
    }
}
