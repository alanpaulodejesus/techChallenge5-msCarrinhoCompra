package carrinhoCompra.carrinhoCompra.exception;

public class ProductNotFoundStockException extends RuntimeException {

    public ProductNotFoundStockException() {
        super("Produto não possui em estoque");
    }
}
