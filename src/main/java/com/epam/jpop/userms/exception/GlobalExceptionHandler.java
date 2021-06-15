package com.epam.jpop.userms.exception;

import com.epam.jpop.userms.model.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFound ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        var error = new ErrorResponse(ex.getMessage(), details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExists ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        var error = new ErrorResponse(ex.getMessage(), details);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> details = ex.getBindingResult().getAllErrors()
                .stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());

        var errorResponse = new ErrorResponse("Validation Failed", details);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
