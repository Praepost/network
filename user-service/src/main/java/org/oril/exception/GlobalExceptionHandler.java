package org.oril.exception;

import lombok.extern.slf4j.Slf4j;
import org.oril.exception.dto.ErrorResponse;
import org.oril.exception.dto.ErrorResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    private ErrorResponseBuilder errorResponseBuilder;

    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<ErrorResponse> handleClientManipulationException(RuntimeException ex, WebRequest request) {
        log.error("юзер уже существует", ex);
        ErrorResponse errorResponse = errorResponseBuilder.builder()
                .setErrorCode(CommonErrorCodes.OPERATION_PERFORM_ERROR)
                .setDescription("Данный пользователь уже существует" + ex.getMessage())
                .setMessage("операция прервана")
                .build();
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}