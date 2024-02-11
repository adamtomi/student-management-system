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
import java.util.UUID;
import java.util.stream.Collectors;

import static com.vamk.backend.util.response.CommonResponses.illegalUuid;
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
        return wrap(() -> ok(this.userRepository.findAll().stream().map(PartialUser::of).collect(Collectors.toList())));
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

            Optional<PartialUser> student = this.userRepository.findById(uuid).map(PartialUser::of);
            return student.isEmpty()
                    ? notFound("student", id)
                    : ok(student.get());
        });
    }

    @PostMapping("/api/students/{id}")
    public ResponseEntity<?> updateStudent(
            @PathVariable String id,
            @RequestBody String emailAddress,
            @RequestBody String firstName,
            @RequestBody String lastName
    ) {
        return wrap(() -> {
            UUID uuid;
            try {
                uuid = UUID.fromString(id);
            } catch (IllegalArgumentException ex) {
                return illegalUuid(id);
            }

            Optional<User> userOpt = this.userRepository.findById(uuid);
            if (userOpt.isEmpty()) return notFound("user", id);

            User user = userOpt.orElseThrow();
            if (emailAddress != null) user.setEmail(emailAddress);
            if (firstName != null) user.setFirstName(firstName);
            if (lastName != null) user.setLastName(lastName);

            this.userRepository.save(user);
            return ok();
        });
    }
}
