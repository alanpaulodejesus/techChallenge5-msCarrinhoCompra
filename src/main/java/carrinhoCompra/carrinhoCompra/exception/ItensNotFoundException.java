package carrinhoCompra.carrinhoCompra.exception;

public class ItensNotFoundException extends RuntimeException {

    public ItensNotFoundException() {
        super("Item não encontrado");
    }

}
