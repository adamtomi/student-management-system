package com.vamk.backend.controller;

import com.vamk.backend.model.Course;
import com.vamk.backend.model.PartialCourse;
import com.vamk.backend.model.User;
import com.vamk.backend.repository.CourseRepository;
import com.vamk.backend.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.vamk.backend.util.response.CommonResponses.badRequest;
import static com.vamk.backend.util.response.CommonResponses.notFound;
import static com.vamk.backend.util.response.CommonResponses.ok;

@RestController
public class CourseController extends AbstractController {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository, UserRepository userRepository) {
        super(LoggerFactory.getLogger(CourseController.class));
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/courses")
    public ResponseEntity<?> getCourses() {
        return wrap(() -> {
            Set<PartialCourse> courses = this.courseRepository.findAll()
                    .stream()
                    .map(PartialCourse::of)
                    .collect(Collectors.toSet());
            return ok(courses);
        });
    }

    @GetMapping("/api/courses/{id}")
    public ResponseEntity<?> getCourse(@PathVariable long id) {
        return wrap(() -> {
            Optional<Course> course = this.courseRepository.findById(id);
            return course.isEmpty()
                    ? notFound("course", id)
                    : ok(PartialCourse.of(course.orElseThrow()));
        });
    }

    @PutMapping("/api/courses")
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        return wrap(() -> {
            Course result = this.courseRepository.save(new Course(course.getName(), course.getTeacherName()));
            return ok(result.getId());
        });
    }

    @PostMapping("/api/courses/{id}")
    public ResponseEntity<?> updateCourse(
            @PathVariable long id,
            @RequestBody Course data
    ) {
        return wrap(() -> {
            Optional<Course> courseOpt = this.courseRepository.findById(id);
            if (courseOpt.isEmpty()) return notFound("course", id);

            Course course = courseOpt.orElseThrow();
            if (data.getName() != null) course.setName(data.getName());
            if (data.getTeacherName() != null) course.setTeacherName(data.getTeacherName());

            this.courseRepository.save(course);
            return ok();
        });
    }

    @PostMapping("/api/courses/{id}/enroll/{userId}")
    public ResponseEntity<?> enrollToCourse(
            @PathVariable long id,
            @PathVariable long userId
    ) {
        return wrap(() -> {
            Optional<Course> courseOpt = this.courseRepository.findById(id);
            if (courseOpt.isEmpty()) return notFound("course", id);

            Optional<User> userOpt = this.userRepository.findById(userId);
            if (userOpt.isEmpty()) return notFound("user", userId);

            Course course = courseOpt.orElseThrow();
            User user = userOpt.orElseThrow();
            if (course.getUsers().contains(user)) return badRequest("This student is already enrolled to this course.");

            course.getUsers().add(user);
            this.courseRepository.save(course);
            return ok();
        });
    }
}
