package com.vamk.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String teacherName;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "enrollments", joinColumns = @JoinColumn(name = "userId"))
    private Set<User> users;

    public Course() {}

    public Course(String name, String teacherName) {
        this.name = name;
        this.teacherName = teacherName;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherName() {
        return this.teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUser(Set<User> users) {
        this.users = users;
    }
}
