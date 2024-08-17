package techclallenge5.fiap.com.msCarrinhoCompra.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)private Long id;

    private String userId;

    @ElementCollection
    private List<Item> items;

    // getters e setters
}