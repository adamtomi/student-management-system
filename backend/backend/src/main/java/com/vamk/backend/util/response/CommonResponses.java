package com.vamk.backend.util.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.vamk.backend.util.response.ResponseBody.failure;
import static com.vamk.backend.util.response.ResponseBody.success;

public final class CommonResponses {
    private static final ResponseEntity<?> INTERNAL_SERVER_ERROR = ResponseEntity.internalServerError()
            .body(failure("An unexpected server error occurred"));

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

    public static ResponseEntity<?> illegalUuid(String id) {
        return ResponseEntity.badRequest()
                .body(failure("The provided uuid ('%s') is invalid.".formatted(id)));
    }

    public static ResponseEntity<?> notFound(String type, String id) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(failure("Resource '%s' with '%s' was not found.".formatted(type, id)));
    }
}
