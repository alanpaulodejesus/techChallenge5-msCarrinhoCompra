package techclallenge5.fiap.com.msCarrinhoCompra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ShoppingCartService {

    @Autowiredprivate ShoppingCartRepository repository;

    public Mono<ShoppingCart> getCartByUserId(String userId) {
        return repository.findByUserId(userId)
                .defaultIfEmpty(newShoppingCart(userId));
    }

    public Mono<ShoppingCart> addItemToCart(String userId, String productId, int quantity) {
        return repository.findByUserId(userId)
                .defaultIfEmpty(newShoppingCart(userId))
                .flatMap(cart -> {
                    cart.addItem(productId, quantity);
                    return repository.save(cart);
                });
    }

    public Mono<ShoppingCart> removeItemFromCart(String userId, String productId) {
        return repository.findByUserId(userId)
                .flatMap(cart -> {
                    cart.removeItem(productId);
                    return repository.save(cart);
                });
    }
}
