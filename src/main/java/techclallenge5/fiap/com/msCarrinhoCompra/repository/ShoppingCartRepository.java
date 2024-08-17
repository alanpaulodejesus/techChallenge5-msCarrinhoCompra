package techclallenge5.fiap.com.msCarrinhoCompra.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
public interface ShoppingCartRepository extends Reactive CrudRepository<ShoppingCart, Long> {
    Mono<ShoppingCart> findByUserId(String userId);
}
