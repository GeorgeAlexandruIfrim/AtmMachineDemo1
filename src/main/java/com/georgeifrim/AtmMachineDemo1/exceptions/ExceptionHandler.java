package com.georgeifrim.AtmMachineDemo1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({NotEnoughMoney.class})
    public ResponseEntity<ErrorResponse> handle(NotEnoughMoney notEnoughMoney){
        var errResponse = new ErrorResponse(notEnoughMoney.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errResponse);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({IllegalAmount.class})
    public ResponseEntity<ErrorResponse> handle(IllegalAmount illegalAmount){
        var errResponse = new ErrorResponse(illegalAmount.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errResponse);
    }

}
