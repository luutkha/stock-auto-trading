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
import trading.stock.stocktrading.dtos.responses.ValidateTokenResponseDTO;

import java.io.IOException;

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
    protected ResponseEntity<ValidateTokenResponseDTO> handleExpiredToken(RuntimeException ex, WebRequest request) {
        log.error("[ADVICE] TOKEN EXCEPTION {} ", ex.getMessage());
        log.error("[Session] {} ", request.getSessionId());
        String errorMessage;
        errorMessage = generateErrorMessage(ex);
        ValidateTokenResponseDTO bodyOfResponse = ValidateTokenResponseDTO.builder().isValid(false).message(errorMessage).build();
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(value = {AuthenticationException.class})
    protected ResponseEntity<Object> handleLoginFail(RuntimeException ex, WebRequest request) {
        log.info("[ADVICE] LOGIN FAIL {}", ex.getMessage());
        log.error("[Session] {} ", request.getSessionId());
        String bodyOfResponse = "Username/Password wrong";
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {IOException.class})
    protected ResponseEntity<Object> handleIOeException(RuntimeException ex, WebRequest request) {
        log.info("[ADVICE] IOException {}", ex.getMessage());
        log.error("[Session] {} ", request.getSessionId());
        String bodyOfResponse = "IOException caught";
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleDuplicateEmailException(RuntimeException ex, WebRequest request) {
        log.info("[ADVICE] RuntimeException {}", ex.getMessage());
        log.error("[Session] {} ", request.getSessionId());
        String bodyOfResponse = ex.getMessage();
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(value = {Exception.class})
//    protected ResponseEntity<Object> handleException(Exception ex) {
//        log.info("[ADVICE] Exception");
//        String bodyOfResponse = "Exception caught " + ex.getMessage();
//        return new ResponseEntity<>(bodyOfResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
