package pl.voteapp.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

}
