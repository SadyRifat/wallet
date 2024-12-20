package com.digital.wallet.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class Error {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private OffsetDateTime timestamp;
    private String message;
    private int code;
    private List<ApiSubError> subErrors;

    private Error() {
        timestamp = OffsetDateTime.now();
    }

    public Error(HttpStatus status) {
        this();
        this.status = status;
    }

    Error(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
    }

    Error(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
    }

    Error(HttpStatus status, String message, int code, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.code = code;
    }

    private void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    private void addValidationError(String field, Object rejectedValue, String message) {
        addSubError(new ApiValidationError(field, rejectedValue, message));
    }

    private void addValidationError(String message) {
        addSubError(new ApiValidationError(message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
    }

    void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(objectError.getDefaultMessage());
    }

    void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }


    // Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.

    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }

    void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }


    abstract class ApiSubError {

    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    class ApiValidationError extends ApiSubError {
        private String field;
        private Object rejectedValue;
        private String message;
        private int code;

        public ApiValidationError(String field, Object rejectedValue, String message) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.message = message;
            this.code = 4000;
        }

        ApiValidationError(String message) {
            this.message = message;
        }
    }
}

