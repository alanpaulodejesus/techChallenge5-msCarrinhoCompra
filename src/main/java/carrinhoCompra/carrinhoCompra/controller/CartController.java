package carrinhoCompra.carrinhoCompra.controller;

import carrinhoCompra.carrinhoCompra.model.Cart;
import carrinhoCompra.carrinhoCompra.model.CartItem;
import carrinhoCompra.carrinhoCompra.service.CartService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{userId}/add")
    public Mono<Cart> addItemToCart(@PathVariable Long userId, @RequestBody CartItem item) {
        return cartService.addItemToCart(userId, item);
    }

    @DeleteMapping("/{userId}/remove/{itemId}")
    public Mono<Cart> removeItemFromCart(@PathVariable Long userId, @PathVariable Long itemId) {
        return cartService.removeItemFromCart(userId, itemId);
    }
}

