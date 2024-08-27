package carrinhoCompra.carrinhoCompra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CarrinhoCompraApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarrinhoCompraApplication.class, args);
	}

}
