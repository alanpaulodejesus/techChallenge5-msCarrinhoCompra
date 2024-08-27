package carrinhoCompra.carrinhoCompra.exception;

public class UsersNotFoundException extends RuntimeException {

    public UsersNotFoundException() {
        super("User not found");
    }

}
