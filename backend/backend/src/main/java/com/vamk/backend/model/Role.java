package com.vamk.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Table
@Entity
public class Role implements GrantedAuthority {
    @Id
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<Student> students;

    @Override
    public String getAuthority() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return this.students;
    }

    public void setStudents(Set<Student> users) {
        this.students = users;
    }
}
