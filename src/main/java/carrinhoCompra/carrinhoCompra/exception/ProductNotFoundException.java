package carrinhoCompra.carrinhoCompra.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Produto não encontrado");
    }
}
