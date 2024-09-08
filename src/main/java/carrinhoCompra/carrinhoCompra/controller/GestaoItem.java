package carrinhoCompra.carrinhoCompra.controller;

import carrinhoCompra.carrinhoCompra.model.CartItem;
import carrinhoCompra.carrinhoCompra.model.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@FeignClient(name = "itens", url = "http://localhost:8085")
public interface GestaoItem {
    @GetMapping("/item/{id}")
    Flux<CartItem> getItemById(@PathVariable("id") Long id);
}
