package carrinhoCompra.carrinhoCompra.controller;

import carrinhoCompra.carrinhoCompra.dto.CartItemRequestDTO;
import carrinhoCompra.carrinhoCompra.model.Cart;
import carrinhoCompra.carrinhoCompra.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(
            summary = "Criar carrinho",
            description = "Este endpoint cria um carrinho para um usuário."
    )
    @PostMapping("/create/{userId}")
    public Mono<Cart> createCartForUser(@PathVariable Long userId, @RequestParam String authToken) {
        return cartService.createNewCart(userId, authToken);
    }

    @Operation(
            summary = "Adicionar item no carrinho",
            description = "Este endpoint adiciona itens a um carrinho para um usuário."
    )
    @PostMapping("/{userId}/add")
    public Flux<Cart> addItemToCart(@PathVariable Long userId, @RequestParam String authToken, @RequestBody CartItemRequestDTO requestDTO) {
        return cartService.addItemToCart(userId,authToken, requestDTO);
    }

    @Operation(
            summary = "Alterar status do carrinho",
            description = "Este endpoint alterar sattus do carrinho."
    )
    @PutMapping("/{userId}/update-status")
    public Mono<Cart> updateStatusCart(@PathVariable Long userId, @RequestParam String authToken) {
        return cartService.updateStatusToCart(userId, authToken);
    }

    @Operation(
            summary = "Finalizar status do carrinho",
            description = "Este endpoint finalizar itens do carrinho."
    )
    @PostMapping("/{userId}/update-status")
    public Flux<Cart> finishStatusCart(@PathVariable Long userId, @RequestParam String authToken) {
        return cartService.finishStatusToCart(userId, authToken);
    }

    @Operation(
            summary = "Excluir item no carrinho",
            description = "Este endpoint exclui itens a um carrinho para um usuário."
    )
    @DeleteMapping("/{userId}/remove/{itemId}")
    public Mono<Cart> removeItemFromCart(@PathVariable Long userId, @RequestParam String authToken, @PathVariable Long itemId) {
        return cartService.removeItemFromCart(userId, authToken, itemId);
    }

    @Operation(
            summary = "Obter carrinho por usuário",
            description = "Este endpoint retorna o carrinho de um usuário com base no ID do usuário."
    )
    @GetMapping("/{userId}")
    public Flux<Cart> getCartByUserId(@PathVariable Long userId, @RequestParam String authToken) {
        return cartService.getCartByUser(userId, authToken);
    }

}

