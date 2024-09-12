package carrinhoCompra.carrinhoCompra.config;

import carrinhoCompra.carrinhoCompra.exception.ItensNotFoundException;
import carrinhoCompra.carrinhoCompra.exception.ProductNotFoundException;
import carrinhoCompra.carrinhoCompra.exception.UnauthorizedAccessException;
import carrinhoCompra.carrinhoCompra.exception.UsersNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UsersNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UsersNotFoundException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), List.of(ex.getMessage()));
    }

    @ExceptionHandler(ItensNotFoundException.class)
    public ResponseEntity<Object> handleItensNotFoundException(ItensNotFoundException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), List.of(ex.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), List.of(ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não Autorizado");
    }


    private ResponseEntity<Object> buildResponseEntity(final HttpStatus httpStatus,
                                                       final String message,
                                                       final List<String> errors) {
        final ApiError apiError =
                new ApiError(httpStatus.getReasonPhrase(), httpStatus.value(), errors, message, LocalDateTime.now());

        return ResponseEntity.status(httpStatus).body(apiError);
    }
}