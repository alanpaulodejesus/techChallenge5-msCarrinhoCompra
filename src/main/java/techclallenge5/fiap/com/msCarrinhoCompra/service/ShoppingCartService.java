package techclallenge5.fiap.com.msCarrinhoCompra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import techclallenge5.fiap.com.msCarrinhoCompra.model.ShoppingCart;
import techclallenge5.fiap.com.msCarrinhoCompra.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository repository;

    public Mono<ShoppingCart> getCartByUserId(String userId) {
        return repository.findByUserId(userId)
                .defaultIfEmpty(new ShoppingCart(userId));
    }

    public Mono<ShoppingCart> addItemToCart(String userId, String productId, int quantity) {
        return repository.findByUserId(userId)
                .defaultIfEmpty(new ShoppingCart(userId))
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
