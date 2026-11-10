package org.siddardha.exception;

import java.time.Instant;
import java.util.List;

// The one error format every failing request returns (requirement R32).
public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,       // e.g. "Not Found"
        String message,     // human-readable explanation
        String path,        // e.g. "/api/products/42"
        List<FieldError> fieldErrors   // only filled for validation failures, otherwise empty
) {
    public record FieldError(String field, String message) {
    }
}
