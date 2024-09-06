package carrinhoCompra.carrinhoCompra.dto;

import jakarta.validation.constraints.NotNull;

public class CartItemRequestDTO {
    @NotNull
    private Long itemId;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
