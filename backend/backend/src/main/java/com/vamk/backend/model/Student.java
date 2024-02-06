package com.vamk.backend.model;

import java.util.UUID;

public record Student(UUID id, String email, String firstName, String lastName) {}
