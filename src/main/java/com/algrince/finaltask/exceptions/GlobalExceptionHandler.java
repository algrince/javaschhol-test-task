package com.algrince.finaltask.exceptions;

import com.algrince.finaltask.dto.ErrorMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;



@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessageDTO> handleResourceNotFoundException(
            ResourceNotFoundException e) {

        log.warn("Client tries no access resource that does not exist");

        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();

        errorMessageDTO.setStatus(HttpStatus.NOT_FOUND);
        errorMessageDTO.setMessage(e.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessageDTO);

    }
}
