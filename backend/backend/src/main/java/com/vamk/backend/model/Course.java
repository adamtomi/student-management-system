package com.vamk.backend.model;

import java.util.UUID;

/* The property "teacher" represents the teacher's name */
public record Course(UUID id, String name, String teacher) {}