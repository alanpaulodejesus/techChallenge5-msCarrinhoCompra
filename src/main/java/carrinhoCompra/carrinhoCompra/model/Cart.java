package carrinhoCompra.carrinhoCompra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.io.IOException;
import java.util.List;

@Table("cart")
public class Cart {

    @Id
    private Long id;
    private Long userId;
    private Double totalValue;

    @JsonIgnore
    private String itemsJson;
    @Transient
    private List<CartItem> items;
    private Status status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }
    public List<CartItem> getItems() {
        if (items == null && itemsJson != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                items = mapper.readValue(itemsJson, new TypeReference<List<CartItem>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return items;
    }

    public void setItems(List<CartItem> items) throws JsonProcessingException {
        this.items = items;
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.itemsJson = mapper.writeValueAsString(items);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
