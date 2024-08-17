package techclallenge5.fiap.com.msCarrinhoCompra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import techclallenge5.fiap.com.msCarrinhoCompra.model.ShoppingCart;
import techclallenge5.fiap.com.msCarrinhoCompra.service.ShoppingCartService;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService service;

    @GetMapping("/{userId}")
    public Mono<ShoppingCart> getCart(@PathVariable String userId) {
        return service.getCartByUserId(userId);
    }

    @PostMapping("/{userId}/add")
    public Mono<ShoppingCart> addItem(@PathVariable String userId, @RequestParam String productId, @RequestParam int quantity) {
        return service.addItemToCart(userId, productId, quantity);
    }

    @PostMapping("/{userId}/remove")
    public Mono<ShoppingCart> removeItem(@PathVariable String userId, @RequestParam String productId) {
        return service.removeItemFromCart(userId, productId);
    }
}
