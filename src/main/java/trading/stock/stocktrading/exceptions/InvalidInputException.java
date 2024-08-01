package trading.stock.stocktrading.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Setter
public class InvalidInputException extends RuntimeException {
    private final  List<FieldError> errors;

    public InvalidInputException(String message, List<FieldError> errors) {
        super(message);
        this.errors = errors;
    }

}
