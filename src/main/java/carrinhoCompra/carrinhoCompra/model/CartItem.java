package carrinhoCompra.carrinhoCompra.model;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Generated;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

@Table("itens")
public class CartItem {
    @Id
    @Generated
    @Schema(hidden = true)
    private Long id;
    private Long itemId;
    private String descricao;
    private Long productId;
    private int quantity;
    private float precoTotal;
    private float precoUnitario;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    @Schema(hidden = true)
    private Long cartId;

    public float getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(float precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public float getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(float precoTotal) {
        this.precoTotal = precoTotal;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
