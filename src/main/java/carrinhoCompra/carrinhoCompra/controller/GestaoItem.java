package carrinhoCompra.carrinhoCompra.controller;

import carrinhoCompra.carrinhoCompra.model.CartItem;
import carrinhoCompra.carrinhoCompra.model.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@FeignClient(name = "Itens", url = "http://localhost:8080")
public interface GestaoItem {
    @GetMapping("/item/{id}")
    Mono<CartItem> getItemById(@PathVariable("id") Long id);
}
