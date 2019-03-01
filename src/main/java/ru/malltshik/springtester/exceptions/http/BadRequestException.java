package ru.malltshik.springtester.exceptions.http;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
