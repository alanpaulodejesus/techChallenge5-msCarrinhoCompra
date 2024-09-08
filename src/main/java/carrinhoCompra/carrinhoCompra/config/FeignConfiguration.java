//package carrinhoCompra.carrinhoCompra.config;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class FeignConfiguration implements RequestInterceptor {
//
//    private final TokenProvider tokenProvider;
//
//    public FeignConfiguration(TokenProvider tokenProvider) {
//        this.tokenProvider = tokenProvider;
//    }
//
//    @Override
//    public void apply(RequestTemplate template) {
//        String token = tokenProvider.getBearerToken();
//        if (token != null) {
//            template.header("Authorization", "Bearer " + token);
//        }
//    }
//}