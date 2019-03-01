package ru.malltshik.springtester.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.malltshik.springtester.exceptions.http.BadRequestException;
import ru.malltshik.springtester.exceptions.http.ForbiddenException;
import ru.malltshik.springtester.exceptions.http.NotFoundException;

@ControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity handleException(Throwable exception) {
        return ResponseEntity.status(500).build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleException(NotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleException(BadRequestException exception) {
        return ResponseEntity.badRequest().build();
    }
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity handleException(ForbiddenException exception) {
        return ResponseEntity.status(403).build();
    }

}
