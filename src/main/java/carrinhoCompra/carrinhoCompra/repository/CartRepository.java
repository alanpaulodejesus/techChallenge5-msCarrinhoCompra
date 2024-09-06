package carrinhoCompra.carrinhoCompra.repository;

import carrinhoCompra.carrinhoCompra.model.Cart;
import carrinhoCompra.carrinhoCompra.model.Status;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartRepository extends ReactiveCrudRepository<Cart, Long> {
    Mono<Cart> findByUserId(Long userId);
    Flux<Cart> findByUserIdAndStatusNot(Long userId, Status status);
}
