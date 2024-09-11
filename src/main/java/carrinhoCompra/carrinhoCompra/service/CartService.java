package carrinhoCompra.carrinhoCompra.service;

import carrinhoCompra.carrinhoCompra.controller.GestaoItem;
import carrinhoCompra.carrinhoCompra.controller.UserClient;
import carrinhoCompra.carrinhoCompra.dto.CartItemRequestDTO;
import carrinhoCompra.carrinhoCompra.exception.UnauthorizedAccessException;
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
    private String authToken;

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
                        return createNewCart(userId, authToken);
                    } else {
                        return Mono.just(cart);
                    }
                }).switchIfEmpty(Mono.defer(() -> createNewCart(userId, authToken)));
    }

    public Mono<Cart> createNewCart(Long userId, String authToken) {
        validateClient(userId, authToken);
        getCartByUserId(userId);
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setStatus(Status.INICIALIZADO);
        return cartRepository.save(cart);
    }

    public Flux<Cart> addItemToCart(Long userId, String authToken, CartItemRequestDTO requestDTO) {
        validateClient(userId, authToken);

        String itemId = String.valueOf(requestDTO.getItemId());
        Integer quantity = requestDTO.getQuantity();

        //MOCK
        CartItem externalItem = new CartItem();
        externalItem.setItemId(itemId);
        externalItem.setDescricao("Descrição Mockada");
        externalItem.setProductId(123L);
        externalItem.setPrecoUnitario(50.0f);
        externalItem.setQuantity(quantity);
        externalItem.setPrecoTotal(quantity*externalItem.getPrecoUnitario());


//        return gestaoItem.getItemById(itemId)
//                .flatMap(externalItem -> {
//                    CartItem item = new CartItem();
//                    item.setItemId(externalItem.getItemId());
//                    item.setDescricao(externalItem.getDescricao());
//                    item.setProductId(externalItem.getProductId());
//                    item.setPrecoUnitario(externalItem.getPrecoUnitario());
//                    item.setQuantity(requestDTO.getQuantity());
//                    item.setPrecoTotal(item.getQuantity()*item.getPrecoUnitario());

        return cartRepository.findByUserIdAndStatusNot(userId, Status.FINALIZADO)
                .flatMap(cart -> {
                    externalItem.setCartId(cart.getId());
                    return cartItemRepository.save(externalItem)
                            .then(cartItemRepository.findByCartId(cart.getId()).collectList())
                            .map(items -> {
                                try {
                                    cart.setItems(items);
//                                    double total = 0.0;
//                                    for(CartItem item1 : items){
//                                        total+=item.getPrecoTotal();
//                                    }
//                                    cart.setTotalValue(total);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                                return cartRepository.save(cart).thenReturn(cart);
                            })
                            .flatMap(mono -> mono);
                    });
                //});
      }


    public Mono<Cart> updateStatusToCart(Long userId, String authToken) {
        validateClient(userId, authToken);
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

    public Flux<Cart> finishStatusToCart(Long userId, String authToken) {
        validateClient(userId, authToken);
        return cartRepository.findByUserIdAndStatusNot(userId, Status.FINALIZADO)
                .flatMap(cart -> {
                    cart.setStatus(Status.FINALIZADO);
                    return cartRepository.save(cart);
                });
    }

    private void validateClient(Long clientId, String authToken) {
        try {
            userClient.getUserById(clientId,  "Bearer " + authToken);
        } catch (FeignException.NotFound e) {
            throw new UsersNotFoundException();
        }catch (FeignException.Unauthorized e){
            throw new UnauthorizedAccessException();
        }
    }


    public Mono<Cart> removeItemFromCart(Long userId, String authToken, Long itemId) {
        validateClient(userId, authToken);
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