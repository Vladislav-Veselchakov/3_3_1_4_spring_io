package web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import web.model.Quote;
import web.model.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SomeController {
    private final RestTemplate restTemplate;

    public SomeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String koren() {
        return "<!DOCTYPE html> <html>" +
                "<body><h3>Hello VL</h3></body></html>";
    }

    @GetMapping("/quote")
    public Quote getQuote() throws Exception {
        int aa = 111;


        String squote = restTemplate.getForObject(
                "https://quoters.apps.pcfone.io/api/random", String.class);

// раскоментить, посмотреть:
        ObjectMapper mapper = new ObjectMapper();
        Quote Quote1 = mapper.readValue(squote, Quote.class);
        System.out.println(squote);


        Quote quote = restTemplate.getForObject(
                "https://quoters.apps.pcfone.io/api/random", Quote.class);

//        ResponseEntity qts = restTemplate1.getForEntity(
//                "https://quoters.apps.pcfone.io/api/random",
//                Quote[].class);


        int bb = 222;
        return quote;
    }

    @GetMapping("/getUsers")
    public Object[] getUsers(){
        int aa = 111;

//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy", 8080));
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        requestFactory.setProxy(proxy);
//
//        RestTemplate restTemplate1 = new RestTemplate(requestFactory);

/**    ////////////// This works OK (home): //////////////////////////////////////////////
        ResponseEntity<Object[]> response = restTemplate.getForEntity(
            "http://91.241.64.178:7081/api/users",
              //  "https://api.stackexchange.com/2.3/users?order=desc&sort=creation&site=stackoverflow",
                Object[].class);
        Object[] objs = response.getBody();
*/


/**        /////////////////////// It works OK home /////////////////////////////////////////
        ResponseEntity response = restTemplate.getForEntity("http://91.241.64.178:7081/api/users",
                User[].class);
        User[] users = (User[]) response.getBody();
        // return users;
*/

        String URL = "http://91.241.64.178:7081/api/users";

///////////////////////// берем список user'oB //////////////////////////////
        ResponseEntity response = restTemplate.getForEntity(URL, String.class);
        String sUsers = (String) response.getBody();

        // берем id сессии:
        String sSesionId =  response.getHeaders().get("Set-Cookie").get(0);

        Object[] objects = {sUsers, "session_id: \n\n" + sSesionId + "   \"", "add here"};
//        String strObjects = "{\"arr\":" + Arrays.toString(objects) + "}";
        String strObjects = Arrays.toString(objects);
        String indented = (new JSONObject()).toString(4); // не работает!!

        System.out.println(indented);

//////////////////////// добавляем user'a ///////////////////////////////////////////
        User user2add = new User(3L, "James", "Brown",  (byte) 2);
        // MultiValueMap<String, String> headers = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Set-Cookie", "JSESSIONID=28DA658A4CA3E853A00FB92ACEFCD1D5; Path=/; HttpOnly");
        headers.add("Set-Cookie", "28DA658A4CA3E853A00FB92ACEFCD1D5");

        HttpEntity<User> request = new HttpEntity<>(user2add, headers);

//        User foo = restTemplate.postForObject(URL, request, User.class);
        ResponseEntity foo = restTemplate.postForEntity(URL, request, String.class);

        objects[2] = "<br>1st answer <br> " + foo.getBody();

        response = restTemplate.getForEntity(URL, String.class);
        sUsers = (String) response.getBody();

        // берем id сессии:
        sSesionId =  response.getHeaders().get("Set-Cookie").get(0);

        objects[0] = sUsers;
        objects[1] =  "session_id: \n\n" + sSesionId;
        objects[2] = foo.getBody();
//        String strObjects = "{\"arr\":" + Arrays.toString(objects) + "}";
         strObjects = Arrays.toString(objects);
         indented = (new JSONObject()).toString(4); // не работает!!

        System.out.println(indented);

///////////////////////// update user ///////////////////////////////////////////////
//        Foo updatedInstance = new Foo("newName");
        user2add.setName("Thomas");
        user2add.setLastName("Shelby");
//        updatedInstance.setId(createResponse.getBody().getId());
//        String resourceUrl =
//                fooResourceUrl + '/' + createResponse.getBody().getId();
//        HttpEntity<Foo> requestUpdate = new HttpEntity<>(updatedInstance, headers);
        HttpEntity<User> request1 = new HttpEntity<>(user2add, headers);

        response = restTemplate.exchange(URL, HttpMethod.POST, request1, String.class);

        // Сработало :)) И равно "cea2a2"
        /// response.getBody()
        return objects;

    }
}
