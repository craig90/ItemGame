package com.craig.itemgame.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidItemMoveException.class)
    public ResponseEntity<ErrorResponse> handleInvalidMove(InvalidItemMoveException ex) {
        
        return ResponseEntity.badRequest().body(
            new ErrorResponse("INVALID_ITEM_MOVE", ex.getMessage())
        );
    }

    @ExceptionHandler(CharacterNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCharacterNotFound(CharacterNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new ErrorResponse("CHARACTER_NOT_FOUND", ex.getMessage())
        );
    }


}
