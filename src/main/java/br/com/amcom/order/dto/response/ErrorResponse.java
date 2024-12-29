package br.com.amcom.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse extends Response {

    private HttpStatus status;

    private List<String> errors;

}
