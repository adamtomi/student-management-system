package com.vamk.backend.util;

import static java.util.Objects.requireNonNull;

/*
 * Very ugly response class. The sole purpose of this
 * is to make returning standard response objects
 * somewhat easier. Jackson will then take this object
 * and transform it into a proper JSON string.
 *
 * Mainly two types of responses:
 * - Error responses: Response({ success: false, status: <error status code>, message: string })
 * - Success responses: Response({ success: true, status: <success status code>, data: Object })
 */
public abstract class Response {
    public final boolean success;
    public final int status;

    private Response(boolean success, int status) {
        this.success = success;
        this.status = status;
    }

    private static final class SuccessResponse<T> extends Response {
        public final T data;

        private SuccessResponse(int status, T data) {
            super(true, status);
            this.data = data;
        }
    }

    private static final class FailureResponse extends Response {
        public final String message;

        private FailureResponse(int status, String message) {
            super(false, status);
            this.message = message;
        }
    }

    public static <T> SuccessResponseBuilder<T> success(int status) {
        return new SuccessResponseBuilder<>(status);
    }

    public static FailureResponseBuilder failure(int status) {
        return new FailureResponseBuilder(status);
    }

    public interface ResponseBuilder {

        Response build();
    }

    public static final class SuccessResponseBuilder<T> implements ResponseBuilder {
        private final int status;
        private T data;

        private SuccessResponseBuilder(int status) {
            this.status = status;
        }

        public SuccessResponseBuilder<T> withData(T data) {
            this.data = data;
            return this;
        }

        @Override
        public Response build() {
            // Data may be nullable, so we don't need to
            // check for null values.
            return new SuccessResponse<>(this.status, this.data);
        }
    }

    public static final class FailureResponseBuilder implements ResponseBuilder {
        private final int status;
        private String message;

        private FailureResponseBuilder(int status) {
            this.status = status;
        }

        public FailureResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        @Override
        public Response build() {
            requireNonNull(this.message, "Message may not be null");
            return new FailureResponse(this.status, this.message);
        }
    }
}
