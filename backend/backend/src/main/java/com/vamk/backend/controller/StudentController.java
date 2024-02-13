package com.vamk.backend.controller;

import com.vamk.backend.model.PartialUser;
import com.vamk.backend.model.User;
import com.vamk.backend.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.vamk.backend.util.response.CommonResponses.notFound;
import static com.vamk.backend.util.response.CommonResponses.ok;

@RestController
public class StudentController extends AbstractController {
    private final UserRepository userRepository;

    @Autowired
    public StudentController(UserRepository userRepository) {
        super(LoggerFactory.getLogger(StudentController.class));
        this.userRepository = userRepository;
    }

    @GetMapping("/api/students")
    public ResponseEntity<?> getStudents() {
        return wrap(() -> ok(this.userRepository.findAll().stream().map(PartialUser::of).collect(Collectors.toSet())));
    }

    @GetMapping("/api/students/{id}")
    public ResponseEntity<?> getStudent(@PathVariable long id) {
        return wrap(() -> {
            Optional<PartialUser> student = this.userRepository.findById(id).map(PartialUser::of);
            return student.isEmpty()
                    ? notFound("student", id)
                    : ok(student.orElseThrow());
        });
    }

    @PostMapping("/api/students/{id}")
    public ResponseEntity<?> updateStudent(
            @PathVariable long id,
            @RequestBody User data
    ) {
        return wrap(() -> {
            Optional<User> userOpt = this.userRepository.findById(id);
            if (userOpt.isEmpty()) return notFound("user", id);

            User user = userOpt.orElseThrow();
            if (data.getEmail() != null) user.setEmail(data.getEmail());
            if (data.getFirstName() != null) user.setFirstName(data.getFirstName());
            if (data.getLastName() != null) user.setLastName(data.getLastName());

            this.userRepository.save(user);
            return ok();
        });
    }
}
