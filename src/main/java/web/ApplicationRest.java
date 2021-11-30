package web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@SpringBootApplication
public class ApplicationRest {

    private static final Logger log = LoggerFactory.getLogger(ApplicationRest.class);

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRest.class, args);

//        RestTemplateBuilder rtBuilder = new RestTemplateBuilder();
//        RestTemplate restTemplate = rtBuilder.build();;
//        Quote quote = restTemplate.getForObject(
//        "https://quoters.apps.pcfone.io/api/random", Quote.class);
//
//        System.out.println("VL text " + (new Date()).toString() + "\r\n" +
//                quote     );
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy", 8080));
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        requestFactory.setProxy(proxy);
//
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        return restTemplate;
         return builder.build();
    }

//    @Bean
//    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
//        return args -> {
//            Quote quote = restTemplate.getForObject(
//                    "https://quoters.apps.pcfone.io/api/random", Quote.class);
//            log.info(quote.toString());
//        };
//    }
}