package carrinhoCompra.carrinhoCompra.service;

import carrinhoCompra.carrinhoCompra.controller.GestaoItem;
import carrinhoCompra.carrinhoCompra.controller.UserClient;
import carrinhoCompra.carrinhoCompra.dto.CartItemRequestDTO;
import carrinhoCompra.carrinhoCompra.exception.ItensNotFoundException;
import carrinhoCompra.carrinhoCompra.exception.UsersNotFoundException;
import carrinhoCompra.carrinhoCompra.model.Cart;
import carrinhoCompra.carrinhoCompra.model.CartItem;
import carrinhoCompra.carrinhoCompra.model.Status;
import carrinhoCompra.carrinhoCompra.repository.CartItemRepository;
import carrinhoCompra.carrinhoCompra.repository.CartRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserClient userClient;
    private final GestaoItem gestaoItem;

    @Autowired
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, UserClient userClient, GestaoItem gestaoItem) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userClient = userClient;
        this.gestaoItem = gestaoItem;
    }


    public Mono<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .flatMap(cart -> {
                    if (cart.getStatus() == Status.FINALIZADO) {
                        return createNewCart(userId);
                    } else {
                        return Mono.just(cart);
                    }
                })
                .switchIfEmpty(Mono.defer(() -> createNewCart(userId)));
    }

    public Mono<Cart> createNewCart(Long userId) {
        //validateClient(userId);
        getCartByUserId(userId);
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setStatus(Status.INICIALIZADO);
        return cartRepository.save(cart);
    }

    public Flux<Cart> addItemToCart(Long userId, CartItemRequestDTO requestDTO) {
        //validateClient(userId);
        //validateItem(item);
        Long itemId = requestDTO.getItemId();

        //MOCK
        CartItem externalItem = new CartItem();
        externalItem.setItemId(itemId);
        externalItem.setDescricao("Descrição Mockada");
        externalItem.setProductId(123L);
        externalItem.setPrecoUnitario(50.0f);
        externalItem.setQuantity(2);
        externalItem.setPrecoTotal(100.0f);


//        return gestaoItem.getItemById(itemId)
//                .flatMap(externalItem -> {
//                    CartItem item = new CartItem();
//                    item.setItemId(externalItem.getItemId());
//                    item.setDescricao(externalItem.getDescricao());
//                    item.setProductId(externalItem.getProductId());
//                    item.setPrecoUnitario(externalItem.getPrecoUnitario());
//                    item.setQuantity(externalItem.getQuantity());
//                    item.setPrecoTotal(externalItem.getPrecoTotal());

        return cartRepository.findByUserIdAndStatusNot(userId, Status.FINALIZADO)
                .flatMap(cart -> {
                    // Associa o item ao carrinho e salva
                    externalItem.setCartId(cart.getId());
                    return cartItemRepository.save(externalItem)
                            .then(cartItemRepository.findByCartId(cart.getId()).collectList())
                            .map(items -> {
                                try {
                                    cart.setItems(items);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                                return cartRepository.save(cart).thenReturn(cart);
                            })
                            .flatMap(mono -> mono);
                });
                //});
    }



    public Mono<Cart> updateStatusToCart(Long userId) {
        //validateClient(userId);
        return cartRepository.findByUserId(userId)
                .flatMap(cart -> {
                    if (cart.getStatus() != Status.FINALIZADO) {
                        cart.setUserId(userId);
                        cart.setStatus(Status.FINALIZADO);
                        return cartRepository.save(cart);
                    } else {
                        return Mono.just(cart);
                    }
                });
    }

    private void validateClient(Long clientId) {
        try {
            this.userClient.getUserById(clientId);
        } catch (FeignException.NotFound e) {
            throw new UsersNotFoundException();
        }
    }

    private void validateItem(Long id) {
        try {
            this.gestaoItem.getItemById( id);
        } catch (FeignException.NotFound e) {
            throw new ItensNotFoundException();
        }
    }

    public Mono<Cart> removeItemFromCart(Long userId, Long itemId) {
        return getCartByUserId(userId)
                .flatMap(cart -> {
                    return cartItemRepository.findByCartId(cart.getId())
                            .filter(item -> item.getId().equals(itemId))
                            .flatMap(cartItemRepository::delete)
                            .then(cartItemRepository.findByCartId(cart.getId()).collectList())
                            .map(items -> {
                                try {
                                    cart.setItems(items);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                                return cartRepository.save(cart).thenReturn(cart);
                            })
                            .flatMap(mono -> mono);
                });
    }
}