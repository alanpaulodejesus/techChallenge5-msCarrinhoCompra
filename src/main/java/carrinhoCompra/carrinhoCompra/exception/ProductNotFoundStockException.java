package carrinhoCompra.carrinhoCompra.exception;

public class ProductNotFoundStockException extends RuntimeException {

    public ProductNotFoundStockException() {
        super("Product not found stock");
    }
}
