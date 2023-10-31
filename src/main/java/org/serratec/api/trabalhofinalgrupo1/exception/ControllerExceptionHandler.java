package org.serratec.api.trabalhofinalgrupo1.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> tratamento(ConstraintViolationException ex) {
	    List<String> erros = new ArrayList<>();
	    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
	        String mensagem = String.format("%s",
	                violation.getMessage(),
	                violation.getInvalidValue()
	        );
	        erros.add(mensagem);
	    }
	    ErroResposta erroResposta = new ErroResposta("Existem Campos Inválidos", LocalDateTime.now(), erros);
	    return ResponseEntity.badRequest().body(erroResposta);
	}

	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> erros = new ArrayList<>(); 

        for(FieldError e: ex.getBindingResult().getFieldErrors()) { 
            erros.add(e.getField() + ": " + e.getDefaultMessage()); 
        }

        ErroResposta erroResposta = new ErroResposta("Existem Campos Inválidos", LocalDateTime.now(), erros);

        return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
    }

	@Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
	
	@ExceptionHandler({EmailException.class, SenhaException.class, UsuarioException.class})
	protected ResponseEntity<?> handleEmailException(Exception ex) {
		return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}
}