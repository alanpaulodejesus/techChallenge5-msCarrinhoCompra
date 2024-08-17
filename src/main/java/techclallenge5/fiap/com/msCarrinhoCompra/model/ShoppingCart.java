package techclallenge5.fiap.com.msCarrinhoCompra.model;


//import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
//@Entity
@Data
@Table("shopping_cart")
public class ShoppingCart {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

//    @ElementCollection
    private List<Item> items;

    public ShoppingCart(String userId) {
    }

    public void addItem(String productId, int quantity) {
    }

    public void removeItem(String productId) {
    }

}