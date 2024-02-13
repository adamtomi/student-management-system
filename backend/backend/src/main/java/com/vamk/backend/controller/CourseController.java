package com.vamk.backend.controller;

import com.vamk.backend.model.Course;
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
        return wrap(() -> ok(this.courseRepository.findAll()));
    }

    @GetMapping("/api/courses/{id}")
    public ResponseEntity<?> getCourse(@PathVariable long id) {
        return wrap(() -> {
            Optional<Course> course = this.courseRepository.findById(id);
            return course.isEmpty()
                    ? notFound("course", id)
                    : ok(course.get());
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

            /*Course course = courseOpt.orElseThrow();
            course.getUsers().add(userOpt.orElseThrow());
            System.out.println(course.getUsers());*/
            // userOpt.

            User user = userOpt.orElseThrow();
            user.getCourses().add(courseOpt.orElseThrow());
            System.out.println(user.getCourses());

            this.userRepository.save(user);
            // this.courseRepository.save(course);
            return ok();
        });
    }
}
