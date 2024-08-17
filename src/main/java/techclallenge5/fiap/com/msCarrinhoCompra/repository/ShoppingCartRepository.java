package techclallenge5.fiap.com.msCarrinhoCompra.repository;

import reactor.core.publisher.Mono;
import techclallenge5.fiap.com.msCarrinhoCompra.model.ShoppingCart;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ShoppingCartRepository extends ReactiveCrudRepository<ShoppingCart, Long> {
    Mono<ShoppingCart> findByUserId(String userId);
}
