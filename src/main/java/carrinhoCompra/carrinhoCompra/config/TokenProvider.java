//package carrinhoCompra.carrinhoCompra.config;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TokenProvider {
//
//    private final RestTemplate restTemplate;
//
//    @Value("${auth.login.url}")
//    private String authLoginUrl;
//
//    @Value("${auth.login.username}")
//    private String username;
//
//    @Value("${auth.login.password}")
//    private String password;
//
//    public TokenProvider(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public String getBearerToken() {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//
//        String requestBody = String.format("{ \"login\": \"%s\", \"password\": \"%s\" }", username, password);
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
//
//        ResponseEntity<TokenResponse> response = restTemplate.exchange(
//                authLoginUrl,
//                HttpMethod.POST,
//                requestEntity,
//                TokenResponse.class
//        );
//
//        return response.getBody() != null ? response.getBody().getToken() : null;
//    }
//}
