package ai.shreds.adapter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ai.shreds.domain.exceptions.DomainInvalidScheduleException;
import ai.shreds.infrastructure.exceptions.InfrastructureCacheAccessException;
import ai.shreds.infrastructure.exceptions.InfrastructureDataAccessException;

import java.time.LocalDateTime;

@ControllerAdvice
public class AdapterGlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception exception) {
        HttpStatus status;
        ErrorResponse errorResponse;

        if (exception instanceof DomainInvalidScheduleException) {
            status = HttpStatus.BAD_REQUEST;
            errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    status.value(),
                    status.getReasonPhrase(),
                    exception.getMessage()
            );
        } else if (exception instanceof InfrastructureDataAccessException ||
                   exception instanceof InfrastructureCacheAccessException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    status.value(),
                    status.getReasonPhrase(),
                    exception.getMessage()
            );
        } else {
            // Log the exception details for debugging
            exception.printStackTrace();

            status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    status.value(),
                    status.getReasonPhrase(),
                    "An unexpected error occurred."
            );
        }

        return new ResponseEntity<>(errorResponse, status);
    }

    private static class ErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;

        public ErrorResponse(LocalDateTime timestamp, int status, String error, String message) {
            this.timestamp = timestamp;
            this.status = status;
            this.error = error;
            this.message = message;
        }

        // Getters and setters

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
