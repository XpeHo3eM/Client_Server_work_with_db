package xpeho3em.myProject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xpeho3em.myProject.exception.DatabaseAccessException;
import xpeho3em.myProject.exception.EntityAlreadyExists;
import xpeho3em.myProject.exception.EntityNotFoundException;

import java.util.Map;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler({EntityAlreadyExists.class,
            DatabaseAccessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> runtimeExceptions (final RuntimeException e) {
        return Map.of("Error", e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notFoundException (final RuntimeException e) {
        return Map.of("Not found", e.getMessage());
    }
}
