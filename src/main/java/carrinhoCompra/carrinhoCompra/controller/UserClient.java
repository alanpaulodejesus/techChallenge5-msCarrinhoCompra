package carrinhoCompra.carrinhoCompra.controller;

import carrinhoCompra.carrinhoCompra.config.FeignConfig;
import carrinhoCompra.carrinhoCompra.model.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.util.UUID;

@FeignClient(name = "Users", url = "http://localhost:8080", configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("/users/{id}")
    Mono<Users> getUserById(@PathVariable("id") UUID id);
}
