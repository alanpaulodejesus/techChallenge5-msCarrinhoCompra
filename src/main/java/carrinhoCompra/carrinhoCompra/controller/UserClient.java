package carrinhoCompra.carrinhoCompra.controller;

import carrinhoCompra.carrinhoCompra.model.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "Users", url = "http://localhost:8080")
public interface UserClient {
    @GetMapping("/users/{id}")
    Users getUserById(@PathVariable("id") Long id, @RequestHeader("Authorization") String authHeader);
}
