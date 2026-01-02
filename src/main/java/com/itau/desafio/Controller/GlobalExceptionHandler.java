package com.itau.desafio.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Quando uma exceção referenciada na anotação é lançada, este método é
    // invocado. No caso, temos uma exceção de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        // Extrai a mensagem de erro da exceção de validação, edita um pouco e retorna
        // uma resposta HTTP com status 400 (Bad Request) e a mensagem de erro no corpo
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage()) // A mensagem padrão foi redefinida na classe Transacao
                .findFirst()
                .orElse("Erro de validação");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
