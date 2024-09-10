package carrinhoCompra.carrinhoCompra.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    @Value("${auth.token}")
    private String authToken;

    @Override
    public void apply(RequestTemplate template) {
        if (authToken != null && !authToken.isEmpty()) {
            template.header("Authorization", "Bearer " + authToken);
        }
    }
}