package fr.flalal.clicker.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalDatabaseServeurException extends RuntimeException {
    public InternalDatabaseServeurException(String message) {
        super(message);
    }
}
