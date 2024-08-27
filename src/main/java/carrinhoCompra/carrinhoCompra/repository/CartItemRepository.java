package carrinhoCompra.carrinhoCompra.repository;

import carrinhoCompra.carrinhoCompra.model.CartItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CartItemRepository extends ReactiveCrudRepository<CartItem, Long> {
    Flux<CartItem> findByCartId(Long cartId);
}