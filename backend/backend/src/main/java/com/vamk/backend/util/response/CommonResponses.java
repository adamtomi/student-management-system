package com.vamk.backend.util.response;

public final class CommonResponses {
    private static final Response INTERNAL_SERVER_ERROR = Response.failure(500)
            .withMessage("An unexpected server error occurred.")
            .build();

    private CommonResponses() {}

    public static Response internalServerError() {
        return INTERNAL_SERVER_ERROR;
    }

    public static Response notFound(String type, String id) {
        return Response.failure(404)
                .withMessage("Resource '%s' with '%s' was not found.".formatted(type, id))
                .build();
    }

    public static Response illegalUuid(String id) {
        return Response.failure(400)
                .withMessage("The provided uuid ('%s') is not valid".formatted(id))
                .build();
    }
}
