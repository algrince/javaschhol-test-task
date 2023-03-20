package com.algrince.finaltask.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ErrorMessageDTO {

    private HttpStatus status;

    private String message;
}
