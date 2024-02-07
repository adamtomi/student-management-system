package com.vamk.backend.controller;

import com.vamk.backend.model.Course;
import com.vamk.backend.model.Student;
import com.vamk.backend.repository.StudentRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

import static com.vamk.backend.util.response.CommonResponses.illegalUuid;
import static com.vamk.backend.util.response.CommonResponses.notFound;
import static com.vamk.backend.util.response.CommonResponses.ok;

@RestController
public class StudentController extends AbstractController {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        super(LoggerFactory.getLogger(StudentController.class));
        this.studentRepository = studentRepository;
    }

    @GetMapping("/api/students")
    public ResponseEntity<?> getStudents() {
        return wrap(() -> ok(this.studentRepository.findAll()));
    }

    @GetMapping("/api/students/{id}")
    public ResponseEntity<?> getStudent(@PathVariable String id) {
        return wrap(() -> {
            UUID uuid;
            try {
                uuid = UUID.fromString(id);
            } catch (IllegalArgumentException ex) {
                return illegalUuid(id);
            }

            Optional<Student> student = this.studentRepository.findById(uuid);
            return student.isEmpty()
                    ? notFound("student", id)
                    : ok(student);
        });
    }

    @PostMapping("/api/students/{id}")
    public ResponseEntity<?> updateStudent(
            @PathVariable String id,
            @RequestBody String emailAddress,
            @RequestBody String firstName,
            @RequestBody String lastName
    ) {

        return ok();
    }

    @GetMapping("/api/course/{id}")
    public ResponseEntity<?> getCourse(@PathVariable String id) {
        Course dummy = new Course(UUID.randomUUID(), "Test", "John Doe");
        return ok(dummy);
    }

    @PostMapping("/api/course/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable String id, @RequestBody String name, @RequestBody String teacher) {
        return ok();
    }
}
