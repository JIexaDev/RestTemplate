package ru.jm.ex;


import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.jm.ex.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

    final static String URL = "http://91.241.64.178:7081/api/users";

    public static void main(String[] args) {

        User user1 = new User(3L, "James", "Brown", (byte) 22);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> requestBody = new HttpEntity<>(user1, headers);
        ResponseEntity<String> result = restTemplate.exchange(URL, HttpMethod.GET, requestBody, String.class);

        List<String> cookies = result.getHeaders().get("Set-Cookie");
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));

        String code;
        result = restTemplate.exchange(URL, HttpMethod.POST, requestBody, String.class);
        code = result.getBody();

        user1.setName("Thomas");
        user1.setLastName("Shelby");

        result = restTemplate.exchange(URL, HttpMethod.PUT, requestBody, String.class);
        code += result.getBody();

        result = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, requestBody, String.class);
        code += result.getBody();
        System.out.println("Ответ: " + code);
    }
}
