package com.vamk.backend.model;

import java.util.Set;
import java.util.stream.Collectors;

/*
 * A partial course contains data from an actual course model
 * that is sent to the frontend. User details are hidden however,
 * only the IDs are sent.
 */
public record PartialCourse(long id, String name, String teacherName, Set<Long> students) {

    public static PartialCourse of(Course course) {
        return new PartialCourse(course.getId(), course.getName(), course.getTeacherName(), course.getUsers().stream().map(User::getId).collect(Collectors.toSet()));
    }
}
