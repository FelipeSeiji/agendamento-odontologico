package com.aula.agendamento_odontologico.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratamentoErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErrosValidacao::new).toList());
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity tratarErro409(DataIntegrityViolationException ex) {
        var erro = new DadosErroDuplicidade("Já existe um cadastro com os dados informados");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    private record DadosErroDuplicidade(String campo) {
    }

    private record DadosErrosValidacao(String field, String defaultMessage) {
        private DadosErrosValidacao(FieldError erros) {
            this(erros.getField(), erros.getDefaultMessage());
        }
    }
}
