package com.example.hospitalcasestudy.advice;

import com.example.hospitalcasestudy.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiAdvice {

    @ExceptionHandler({RecordNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRecordNotFound(RecordNotFoundException recordNotFoundException){
        return recordNotFoundException.getMessage();
    }
}
