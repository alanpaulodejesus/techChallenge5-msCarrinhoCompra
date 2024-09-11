package carrinhoCompra.carrinhoCompra.exception;

public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException() {
        super("Não Autorizado");
    }
}
