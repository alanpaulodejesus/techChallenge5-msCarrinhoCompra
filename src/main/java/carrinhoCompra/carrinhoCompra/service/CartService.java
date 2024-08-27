package carrinhoCompra.carrinhoCompra.service;

import carrinhoCompra.carrinhoCompra.controller.UserClient;
import carrinhoCompra.carrinhoCompra.exception.UsersNotFoundException;
import carrinhoCompra.carrinhoCompra.model.Cart;
import carrinhoCompra.carrinhoCompra.model.CartItem;
import carrinhoCompra.carrinhoCompra.repository.CartItemRepository;
import carrinhoCompra.carrinhoCompra.repository.CartRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserClient userClient;

    @Autowired
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
                    item.setCartId(cart.getId());
                    return cartItemRepository.save(item)
                            .then(cartItemRepository.findByCartId(cart.getId()).collectList())
                            .map(items -> {
                                try {
                                    cart.setItems(items);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                                return cartRepository.save(cart).thenReturn(cart);
                            })
                            .flatMap(mono -> mono);
                });
    }

    private void validateClient(Long clientId) {
        try {
            this.userClient.getUserById(clientId);
        } catch (FeignException.NotFound e) {
            throw new UsersNotFoundException();
        }
    }

    public Mono<Cart> removeItemFromCart(Long userId, Long itemId) {
        return getCartByUserId(userId)
                .flatMap(cart -> {
                    return cartItemRepository.findByCartId(cart.getId())
                            .filter(item -> item.getId().equals(itemId))
                            .flatMap(cartItemRepository::delete)
                            .then(cartItemRepository.findByCartId(cart.getId()).collectList())
                            .map(items -> {
                                try {
                                    cart.setItems(items);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                                return cartRepository.save(cart).thenReturn(cart);
                            })
                            .flatMap(mono -> mono);
                });
    }
}