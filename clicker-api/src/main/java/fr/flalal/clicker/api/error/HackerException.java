package fr.flalal.clicker.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
public class HackerException extends RuntimeException {
    public HackerException(String message) {
        super(message);
    }
}
