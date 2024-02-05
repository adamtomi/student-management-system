package com.vamk.backend.model;

import java.util.UUID;

public record Student(UUID uniqueId, String email, String firstName, String lastName) {}
