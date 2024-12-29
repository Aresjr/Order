package br.com.amcom.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse extends Response {

    private HttpStatus status;

    private String error;

}
