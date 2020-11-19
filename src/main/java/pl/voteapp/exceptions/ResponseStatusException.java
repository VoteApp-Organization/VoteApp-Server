package pl.voteapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "No such entity")
public class ResponseStatusException extends RuntimeException{
    public ResponseStatusException(String message) {
        super(message);
    }
}
