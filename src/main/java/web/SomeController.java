package web;

import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import web.model.Quote;

@RestController
public class SomeController {
    private final RestTemplate restTemplate;

    public SomeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/quote")
    public Quote getQuote() {
        int aa = 111;
        Quote quote = restTemplate.getForObject(
                "https://quoters.apps.pcfone.io/api/random", Quote.class);

        ResponseEntity qts = restTemplate.getForEntity(
                "https://quoters.apps.pcfone.io/api/random",
                Quote[].class);


        int bb = 222;
        return quote;
    }

    @GetMapping("/getUsers")
    public Object[] getUsers(){
        int aa = 111;
        ResponseEntity<Object[]> response = restTemplate.getForEntity(
            "http://91.241.64.178:7081/api/users",
                Object[].class);
        Object[] objs = response.getBody();

        // берем id сессии:
        // response.getHeaders().get("Set-Cookie").get(0);

        ResponseEntity usrs = restTemplate.getForEntity(
                "http://91.241.64.178:7081/api/users",
                User[].class);


        return objs;


    }
}
