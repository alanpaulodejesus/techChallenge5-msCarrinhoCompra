//package carrinhoCompra.carrinhoCompra.service;
//
//import org.junit.jupiter.api.Test;
//import carrinhoCompra.carrinhoCompra.controller.GestaoItem;
//import carrinhoCompra.carrinhoCompra.controller.UserClient;
//import carrinhoCompra.carrinhoCompra.exception.UnauthorizedAccessException;
//import carrinhoCompra.carrinhoCompra.model.Cart;
//import carrinhoCompra.carrinhoCompra.model.CartItem;
//import carrinhoCompra.carrinhoCompra.model.Status;
//import carrinhoCompra.carrinhoCompra.repository.CartItemRepository;
//import carrinhoCompra.carrinhoCompra.repository.CartRepository;
//import feign.FeignException;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class CartServiceTest {
//
//    @Mock
//    private CartRepository cartRepository;
//
//    @Mock
//    private CartItemRepository cartItemRepository;
//
//    @Mock
//    private UserClient userClient;
//
//    @Mock
//    private GestaoItem gestaoItem;
//
//    @InjectMocks
//    private CartService cartService;
//
//    private final String authToken = "validToken";
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetCartByUserId_CartExistsAndNotFinalized() {
//        Long userId = 1L;
//        Cart cart = new Cart();
//        cart.setStatus(Status.INICIALIZADO);
//
//        when(cartRepository.findByUserId(userId)).thenReturn(Mono.just(cart));
//        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(cart));
//
//        Mono<Cart> result = cartService.getCartByUserId(userId);
//
//        assertEquals(Status.INICIALIZADO, result.block().getStatus());
//        verify(cartRepository, never()).save(any(Cart.class));
//    }
//
//    @Test
//    void testGetCartByUserId_CartFinalized_CreatesNewCart() {
//        Long userId = 1L;
//        Cart cart = new Cart();
//        cart.setStatus(Status.FINALIZADO);
//
//        when(cartRepository.findByUserId(userId)).thenReturn(Mono.just(cart));
//        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(cart));
//        doNothing().when(userClient).getUserById(any(Long.class), any(String.class));
//
//        when(cartService.createNewCart(userId, authToken)).thenReturn(Mono.just(cart));
//
//        Mono<Cart> result = cartService.getCartByUserId(userId);
//
//        assertEquals(Status.INICIALIZADO, result.block().getStatus());
//        verify(cartRepository).save(any(Cart.class));
//    }
//
//    @Test
//    void testCreateNewCart_Success() {
//        Long userId = 1L;
//        Cart cart = new Cart();
//        cart.setUserId(userId);
//        cart.setStatus(Status.INICIALIZADO);
//
//        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(cart));
//        doNothing().when(userClient).getUserById(any(Long.class), any(String.class));
//
//        Mono<Cart> result = cartService.createNewCart(userId, authToken);
//
//        assertEquals(Status.INICIALIZADO, result.block().getStatus());
//        verify(cartRepository).save(any(Cart.class));
//    }
//
//    @Test
//    void testCreateNewCart_UnauthorizedException() {
//        Long userId = 1L;
//
//        doThrow(new FeignException.Unauthorized("Unauthorized", null, null, null))
//                .when(userClient).getUserById(any(Long.class), any(String.class));
//
//        assertThrows(UnauthorizedAccessException.class, () -> cartService.createNewCart(userId, authToken).block());
//    }
//
//    @Test
//    void testUpdateStatusToCart_Success() {
//        Long userId = 1L;
//        Cart cart = new Cart();
//        cart.setStatus(Status.INICIALIZADO);
//
//        when(cartRepository.findByUserId(userId)).thenReturn(Mono.just(cart));
//        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(cart));
//        doNothing().when(userClient).getUserById(any(Long.class), any(String.class));
//
//        Mono<Cart> result = cartService.updateStatusToCart(userId, authToken);
//
//        assertEquals(Status.FINALIZADO, result.block().getStatus());
//        verify(cartRepository).save(any(Cart.class));
//    }
//
//    @Test
//    void testUpdateStatusToCart_AlreadyFinalized() {
//        Long userId = 1L;
//        Cart cart = new Cart();
//        cart.setStatus(Status.FINALIZADO);
//
//        when(cartRepository.findByUserId(userId)).thenReturn(Mono.just(cart));
//
//        Mono<Cart> result = cartService.updateStatusToCart(userId, authToken);
//
//        assertEquals(Status.FINALIZADO, result.block().getStatus());
//        verify(cartRepository, never()).save(any(Cart.class));
//    }
//
//    //@Test
//    void testFinishStatusToCart_Success() {
//        Long userId = 1L;
//        Cart cart = new Cart();
//        cart.setStatus(Status.INICIALIZADO);
//
//        //when(cartRepository.findByUserIdAndStatusNot(userId, Status.FINALIZADO)).thenReturn(Mono.just(cart));
//        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(cart));
//        doNothing().when(userClient).getUserById(any(Long.class), any(String.class));
//
//        Flux<Cart> result = cartService.finishStatusToCart(userId, authToken);
//
//        assertEquals(Status.FINALIZADO, result.blockFirst().getStatus());
//        verify(cartRepository).save(any(Cart.class));
//    }
//
//    @Test
//    void testRemoveItemFromCart_Success() {
//        Long userId = 1L;
//        Long itemId = 1L;
//        Cart cart = new Cart();
//        cart.setId(1L);
//        cart.setStatus(Status.INICIALIZADO);
//
//        CartItem cartItem = new CartItem();
//        cartItem.setId(itemId);
//
//        when(cartRepository.findByUserId(userId)).thenReturn(Mono.just(cart));
//        when(cartItemRepository.findByCartId(cart.getId())).thenReturn(Flux.just(cartItem));
//        when(cartItemRepository.delete(any(CartItem.class))).thenReturn(Mono.empty());
//        when(cartItemRepository.findByCartId(cart.getId())).thenReturn(Flux.empty());
//        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(cart));
//        doNothing().when(userClient).getUserById(any(Long.class), any(String.class));
//
//        Mono<Cart> result = cartService.removeItemFromCart(userId, authToken, itemId);
//
//        assertEquals(Status.INICIALIZADO, result.block().getStatus());
//        verify(cartItemRepository).delete(any(CartItem.class));
//    }
//}
