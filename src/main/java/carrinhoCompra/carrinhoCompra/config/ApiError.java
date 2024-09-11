package carrinhoCompra.carrinhoCompra.config;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(String status,
                       Integer code,
                       List<String> errors,
                       String message,
                       @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                       LocalDateTime timestamp) {
}
