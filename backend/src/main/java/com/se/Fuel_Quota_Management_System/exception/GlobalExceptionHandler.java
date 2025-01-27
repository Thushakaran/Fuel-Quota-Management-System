package com.se.Fuel_Quota_Management_System.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle VehicleNotFoundException
    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<?> handleVehicleNotFoundException(VehicleNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse("Vehicle not found", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    // Handle VehicleAlreadyRegisteredException
    @ExceptionHandler(VehicleAlreadyRegisteredException.class)
    public ResponseEntity<?> handleVehicleAlreadyRegisteredException(VehicleAlreadyRegisteredException ex) {
        return new ResponseEntity<>(new ErrorResponse("Vehicle already registered", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // Handle general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse("Internal Server Error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Error response model
    public static class ErrorResponse {
        private String error;
        private String message;

        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
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
