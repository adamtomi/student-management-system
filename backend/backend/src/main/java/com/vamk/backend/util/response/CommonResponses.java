package com.vamk.backend.util.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.vamk.backend.util.response.ResponseBody.failure;
import static com.vamk.backend.util.response.ResponseBody.success;

public final class CommonResponses {
    private static final ResponseEntity<?> INTERNAL_SERVER_ERROR = ResponseEntity.internalServerError()
            .body(failure("An unexpected server error occurred."));

    private CommonResponses() {}

    public static ResponseEntity<?> internalServerError() {
        return INTERNAL_SERVER_ERROR;
    }

    public static ResponseEntity<?> ok(Object data) {
        return ResponseEntity.ok(success(data));
    }

    public static ResponseEntity<?> ok() {
        return ok(null);
    }

    public static ResponseEntity<?> badRequest(String message) {
        return ResponseEntity.badRequest().body(failure(message));
    }

    public static ResponseEntity<?> notFound(String type, long id) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(failure("Resource '%s' with id '%s' was not found.".formatted(type, id)));
    }
}
