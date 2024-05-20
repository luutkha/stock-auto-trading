package trading.stock.stocktrading.configs.advices;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import trading.stock.stocktrading.dtos.ValidateTokenResponse;

@ControllerAdvice
@Log4j2
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {
    private static String generateErrorMessage(RuntimeException ex) {
        String errorMessage = "[JWT] Unknown Exception";
        if (ex instanceof SignatureException) {
            errorMessage = "Signature Exception";
        }
        if (ex instanceof ExpiredJwtException) {
            errorMessage = "Expired JWT Exception";
        }
        if (ex instanceof JwtException) {
            errorMessage = "JWT Exception";
        }
        return errorMessage;
    }

    @ExceptionHandler(value = {SignatureException.class, ExpiredJwtException.class, SignatureException.class, JwtException.class,})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ValidateTokenResponse> handleExpiredToken(RuntimeException ex, WebRequest request) {
        log.error("[ADVICE] TOKEN EXCEPTION ");
        String errorMessage;
        errorMessage = generateErrorMessage(ex);
        ValidateTokenResponse bodyOfResponse = ValidateTokenResponse.builder().isValid(false).message(errorMessage).build();
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(value = {AuthenticationException.class})
    protected ResponseEntity<Object> handleLoginFail(RuntimeException ex, WebRequest request) {
        log.info("[ADVICE] LOGIN FAIL");
        String bodyOfResponse = "Username/Password wrong";
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.UNAUTHORIZED);
    }

}