package carrinhoCompra.carrinhoCompra.model;



import lombok.Generated;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

@Table("cart_item")
public class CartItem {
    @Id
    @Generated
    private Long id;
    private Long productId;
    private int quantity;
    private Long cartId;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
