package trading.stock.stocktrading.exceptions;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
public class InvalidInputException extends RuntimeException {
    List<FieldError> errors;

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, List<FieldError> errors) {
        super(message);
        this.errors = errors;
    }
}
