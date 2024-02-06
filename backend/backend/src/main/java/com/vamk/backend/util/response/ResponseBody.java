package com.vamk.backend.util.response;

public abstract class ResponseBody {
    public final boolean success;

    private ResponseBody(boolean success) {
        this.success = success;
    }

    public static final class Success<T> extends ResponseBody {
        public final T data;

        private Success(T data) {
            super(true);
            this.data = data;
        }
    }

    public static final class Failure extends ResponseBody {
        public final String message;

        private Failure(String message) {
            super(false);
            this.message = message;
        }
    }

    public static <T> Success<T> success(T data) {
        return new Success<>(data);
    }

    public static Failure failure(String message) {
        return new Failure(message);
    }
}
