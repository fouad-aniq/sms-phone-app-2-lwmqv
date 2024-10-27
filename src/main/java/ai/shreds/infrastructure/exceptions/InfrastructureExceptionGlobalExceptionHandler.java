package ai.shreds.infrastructure.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestControllerAdvice
public class InfrastructureExceptionGlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(InfrastructureExceptionGlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        // Log the exception details
        logger.error("An unexpected error occurred: ", exception);

        // Create an error response
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred. Please try again later."
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class ErrorResponse {
        private int statusCode;
        private String message;
    }
}