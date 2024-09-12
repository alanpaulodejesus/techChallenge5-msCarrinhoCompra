package carrinhoCompra.carrinhoCompra.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Users {
    @Id
    private Long id;
    private String username;
    private String email;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
