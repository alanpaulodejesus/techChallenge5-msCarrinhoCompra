package carrinhoCompra.carrinhoCompra.service;

import carrinhoCompra.carrinhoCompra.controller.UserClient;
import carrinhoCompra.carrinhoCompra.exception.UsersNotFoundException;
import carrinhoCompra.carrinhoCompra.model.Cart;
import carrinhoCompra.carrinhoCompra.model.CartItem;
import carrinhoCompra.carrinhoCompra.repository.CartItemRepository;
import carrinhoCompra.carrinhoCompra.repository.CartRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    @Autowired
    private final UserClient userClient;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, UserClient userClient) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userClient = userClient;
    }

    public Mono<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .switchIfEmpty(Mono.defer(() -> createNewCart(userId)));
    }

    private Mono<Cart> createNewCart(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        return cartRepository.save(cart);
    }

    public Mono<Cart> addItemToCart(Long userId, CartItem item) {
        //validateClient(userId);
        return getCartByUserId(userId)
                .flatMap(cart -> {
                    cart.getItems().add(item);
                    return cartRepository.save(cart);
                });
    }

    private void validateClient(Long clientId) {
        try {
            this.userClient.getUserById(clientId );
        } catch (FeignException.NotFound e) {
            throw new UsersNotFoundException();
        }
    }

    public Mono<Cart> removeItemFromCart(Long userId, Long itemId) {
        return getCartByUserId(userId)
                .flatMap(cart -> {
                    cart.getItems().removeIf(item -> item.getId().equals(itemId));
                    return cartRepository.save(cart);
                });
    }

}

