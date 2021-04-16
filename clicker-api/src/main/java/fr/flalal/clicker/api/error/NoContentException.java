package fr.flalal.clicker.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class NoContentException extends RuntimeException {
    public NoContentException(String message) {
        super(message);
    }
}
