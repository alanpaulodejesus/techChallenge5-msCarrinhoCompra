package carrinhoCompra.carrinhoCompra.repository;

import carrinhoCompra.carrinhoCompra.model.Cart;
import carrinhoCompra.carrinhoCompra.model.Status;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CartRepository extends ReactiveCrudRepository<Cart, UUID> {
    Mono<Cart> findByUserId(Long userId);
    Flux<Cart> findByUserIdAndStatusNot(Long userId, Status status);
    Flux<Cart> findByUserIdOrderByIdAsc(Long userId);
}
