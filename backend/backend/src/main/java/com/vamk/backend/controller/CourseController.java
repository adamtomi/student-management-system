package com.vamk.backend.controller;

import com.vamk.backend.model.Course;
import com.vamk.backend.repository.CourseRepository;
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

    @Autowired
    public CourseController(CourseRepository courseRepository) {
        super(LoggerFactory.getLogger(CourseController.class));
        this.courseRepository = courseRepository;
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
    public ResponseEntity<?> createCourse(@RequestBody String name, @RequestBody String teacherName) {
        return wrap(() -> {
            Course result = this.courseRepository.save(new Course(name, teacherName));
            return ok(result.getId());
        });
    }

    @PostMapping("/api/courses/{id}")
    public ResponseEntity<?> updateCourse(
            @PathVariable long id,
            @RequestBody(required = false) String name,
            @RequestBody(required = false) String teacher
    ) {
        return wrap(() -> {
            Optional<Course> courseOpt = this.courseRepository.findById(id);
            if (courseOpt.isEmpty()) return notFound("course", id);

            Course course = courseOpt.orElseThrow();
            if (name != null) course.setName(name);
            if (teacher != null) course.setTeacherName(teacher);

            this.courseRepository.save(course);
            return ok();
        });
    }
}
