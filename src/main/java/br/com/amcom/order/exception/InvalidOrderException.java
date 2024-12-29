package br.com.amcom.order.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidOrderException extends IllegalArgumentException {

    List<String> errors;

    public InvalidOrderException(List<String> errors) {
        this.errors = errors;
    }
}
