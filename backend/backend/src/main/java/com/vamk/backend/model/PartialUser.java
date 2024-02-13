package com.vamk.backend.model;

import java.util.Set;

/*
 * A partial user contains data from an actual user model
 * that is sent to the frontend. Sensitive data (such as
 * passwords and roles) are not included.
 */
public record PartialUser(long id, String firstName, String lastName, String email, Set<Course> courses) {

    public static PartialUser of(User user) {
        return new PartialUser(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getCourses());
    }
}
