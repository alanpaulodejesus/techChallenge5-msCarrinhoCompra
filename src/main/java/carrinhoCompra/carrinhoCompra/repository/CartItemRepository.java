package carrinhoCompra.carrinhoCompra.repository;

import carrinhoCompra.carrinhoCompra.model.CartItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CartItemRepository extends ReactiveCrudRepository<CartItem, Long> {
}