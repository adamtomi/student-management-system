package com.vamk.backend.controller;

import com.vamk.backend.model.Course;
import com.vamk.backend.repository.CourseRepository;
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
public class CourseController extends AbstractController {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository) {
        super(LoggerFactory.getLogger(CourseController.class));
        this.courseRepository = courseRepository;
    }

    @GetMapping("/api/course/{id}")
    public ResponseEntity<?> getCourse(@PathVariable String id) {
        return wrap(() -> {
            UUID uuid;
            try {
                uuid = UUID.fromString(id);
            } catch (IllegalArgumentException ex) {
                return illegalUuid(id);
            }

            Optional<Course> course = this.courseRepository.findById(uuid);
            return course.isEmpty()
                    ? notFound("course", id)
                    : ok(course.get());
        });
    }

    @PostMapping("/api/course/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable String id, @RequestBody String name, @RequestBody String teacher) {
        return wrap(() -> {
            UUID uuid;
            try {
                uuid = UUID.fromString(id);
            } catch (IllegalArgumentException ex) {
                return illegalUuid(id);
            }

            Optional<Course> courseOpt = this.courseRepository.findById(uuid);
            if (courseOpt.isEmpty()) return notFound("course", id);

            Course course = courseOpt.orElseThrow();
            if (name != null) course.setName(name);
            if (teacher != null) course.setTeacherName(teacher);

            this.courseRepository.save(course);
            return ok();
        });
    }
}
