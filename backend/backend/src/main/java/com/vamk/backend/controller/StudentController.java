package com.vamk.backend.controller;

import com.vamk.backend.model.Course;
import com.vamk.backend.model.Student;
import com.vamk.backend.repository.StudentRepository;
import com.vamk.backend.util.CollectionUtil;
import com.vamk.backend.util.response.Response;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.vamk.backend.util.response.CommonResponses.illegalUuid;
import static com.vamk.backend.util.response.CommonResponses.notFound;

@RestController
public class StudentController extends AbstractController {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        super(LoggerFactory.getLogger(StudentController.class));
        this.studentRepository = studentRepository;
    }

    @GetMapping("/api/students")
    public Response getStudents() {
        return wrap(() -> {
            Set<Student> students = CollectionUtil.fromIterable(this.studentRepository.findAll(), HashSet::new);
            return Response.success(200).withData(students).build();
        });
        /*Set<Student> students = CollectionUtil.fromIterable(this.studentRepository.findAll(), HashSet::new);
        return Response.success(200).withData(students).build();*/
    }

    @GetMapping("/api/students/{id}")
    public Response getStudent(@PathVariable String id) {
        return wrap(() -> {
            UUID uuid;
            try {
                uuid = UUID.fromString(id);
            } catch (IllegalArgumentException ex) {
                return illegalUuid(id);
            }

            Optional<Student> student = this.studentRepository.findById(uuid);
            return student.map(x -> Response.success(200).withData(x).build())
                    .orElseGet(() -> notFound("student", id));
        });
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
