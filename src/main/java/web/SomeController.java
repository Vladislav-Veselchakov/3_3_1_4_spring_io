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

import java.net.URL;
import java.util.*;

@RestController
public class SomeController {
    private final RestTemplate restTemplate;
    private String URL;
    private HttpHeaders headers;
    private StringBuilder resp2task;
    private StringBuilder sbResp;
    /** aaa ///////////////////////// swithch on PROXY, if on work: ///////////////////////
     Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy", 8080));
     SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
     requestFactory.setProxy(proxy);

     RestTemplate restTemplate1 = new RestTemplate(requestFactory);
     */

    public SomeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.URL = "http://91.241.64.178:7081/api/users";
        this.headers = new HttpHeaders();
        this.resp2task = new StringBuilder();
        this.sbResp = new StringBuilder("<!DOCTYPE html>\n" +
                "<html lang=\"ru-RU\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>VL Resttemplate response</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<H3>response: </H3>\n");

    }

    @GetMapping("/getAnswer") //////////////////////////// "/getAnswer" //////////////////////////////////////////////
    public String getAnswer(){
        getUsers();
        String ans1 = addUser();
        String ans2 = updateUser();
        String ans3 = deleteUser();

        resp2task.append(ans1).append(ans2).append(ans3);
        sbResp.append("\n<br><br> FULL answer: &nbsp &nbsp" + resp2task + "<br>\n");
        sbResp.append("\n<br>time: ").append((new Date()).toLocaleString()).append("<br>\n");
        sbResp.append("\n<br>\n </body>\n" + "</html>");
        return sbResp.toString();

    }

    ///////////////////////// берем список user'oB //////////////////////////////
    public void getUsers() {
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        String sUsers = response.getBody();

        // берем id сессии:
        String sFullSession = response.getHeaders().get("Set-Cookie").get(0);
//        List<String> lstString = restTemplate.headForHeaders(URL).get("Set-Cookie");
//        String sFullSession = lstString.get(0);

        headers.clear();
        headers.add("Cookie", sFullSession);

        System.out.println("Sesssion ID: " + sFullSession);
        sbResp.append("Session ID: ").append(sFullSession).append("<br>\n");

        sUsers = "{arr:" + sUsers + "}";
        sUsers = (new JSONObject(sUsers)).toString(4);
        sUsers = sUsers.replace(" ", "&nbsp;").replace("\n", "<br>\n");
        sbResp.append("added users: <br>\n").append(sUsers);
    }

    //////////////////////// добавляем user'a ///////////////////////////////////////////
    public String addUser() {
        User user2add = new User(3L, "James", "Brown",  (byte) 2);
        HttpEntity<User> request = new HttpEntity<>(user2add, headers);
        ResponseEntity response = restTemplate.postForEntity(URL, request, String.class);

        String sBody = (String) response.getBody();
        sbResp.append("\n<br>Response #1: " + sBody + "<br>\n");

        return sBody;
    }

  ///////////////////////// update user ///////////////////////////////////////////////
    public String updateUser() {
        User user2add = new User(3L, "James", "Brown",  (byte) 2);
        user2add.setName("Thomas");
        user2add.setLastName("Shelby");

        HttpEntity<User> request = new HttpEntity<>(user2add, headers);
        ResponseEntity response = restTemplate.exchange(URL, HttpMethod.PUT, request, String.class);

        String sBody = (String) response.getBody();
        sbResp.append("\n<br>Response #2: " + sBody + "<br>\n");
        return sBody;
    }

    ///////////////////////////// Delete user //////////////////////////////////////////
    public String deleteUser() {
//        restTemplate.delete(URL + "/3");
        User user2add = new User(3L, "James", "Brown",  (byte) 2);
        user2add.setName("Thomas");
        user2add.setLastName("Shelby");

        HttpEntity<User> request = new HttpEntity<>(user2add, headers);
        ResponseEntity response = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, request, String.class);

        String sBody = (String) response.getBody();
        sbResp.append("\n<br>Response #3: " + sBody + "<br>\n");
        return sBody;
    }


    @GetMapping("/") ///////////////////////////////// "/"   //////////////////////
    public String root() {
        return "<!DOCTYPE html> <html>" +
                "<body><h3>Hello VL</h3></body></html>";
    }

    @GetMapping("/quote") //////////////////////////////////// "/quote" //////////////////////////////////////
    public String getQuote() throws Exception {
        int aa = 111;
        String sFullSession = "JSESSIONID=28DA658A4CA3E853A00FB92ACEFCD1D5; Path=/; HttpOnly";

        String squote = restTemplate.getForObject(
                "https://quoters.apps.pcfone.io/api/random", String.class);

// раскоментить, посмотреть:
        ObjectMapper mapper = new ObjectMapper();
        Quote Quote1 = mapper.readValue(squote, Quote.class);
        System.out.println(squote);


        Quote quote = restTemplate.getForObject(
                "https://quoters.apps.pcfone.io/api/random", Quote.class);

        StringBuilder sbResp = new StringBuilder("<!DOCTYPE html>\n" +
                "<html lang=\"ru-RU\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>VL Resttemplate response</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<H3>response: </H3>\n");


        String indented = (new JSONObject(quote)).toString(4);

        System.out.println(indented);

//        sbResp.append(indented.replace("\n", "<br>")).append("<br> </body>\n" +
        sbResp.append(indented.replace(" ", "&nbsp;").replace("\n", "<br>\n")).append("\n<br>\n </body>\n" +
                "</html>");
        return sbResp.toString();
    }


    @GetMapping("/test") //////////////////////////// "/test" //////////////////////////////////////////////
    public String getTest() throws Exception {
        List<String> lObjects = new ArrayList<>();
        StringBuilder sbResp = new StringBuilder("<!DOCTYPE html>\n" +
                "<html lang=\"ru-RU\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>VL Resttemplate response</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "RESPONSE<BR>\n");

        // берем id сессии:
        String sSesionId = "28DA658A4CA3E853A00FB92ACEFCD1D5";
        sbResp.append("Session ID: ").append(sSesionId).append("<br>\n");

        ///////////////////////// берем список user'oB //////////////////////////////
        User[] arrUsers = {new User(111L, "one", "hren", (byte)11), new User(222L, "one", "hren", (byte)22)};

        ObjectMapper objectMapper = new ObjectMapper();
        String sUsers = objectMapper.writeValueAsString(arrUsers);
        sUsers = "{arr:" + sUsers + "}";
        sUsers = (new JSONObject(sUsers)).toString(4);
        sUsers = sUsers.replace(" ", "&nbsp;").replace("\n", "<br>\n");
        sbResp.append("added users: <br>\n").append(sUsers);

        sbResp.append("\n<br>time: ").append((new Date()).toLocaleString()).append("<br>\n");
        sbResp.append("\n<br>\n </body>\n" + "</html>");
        return sbResp.toString();
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}



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
