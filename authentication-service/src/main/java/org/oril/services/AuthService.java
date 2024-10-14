package org.oril.services;

import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.oril.entities.AuthRequest;
import org.oril.entities.AuthResponse;
import org.oril.entities.TokenRequest;
import org.oril.entities.UserVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@AllArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public AuthResponse register(AuthRequest request) {
        //do validation if user exists in DB
        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));


//
//        UserVO registeredUser = WebClient.create("http://localhost:8080")
//                .post()
//                .uri("user-service/users")
//                .bodyValue(request)
//                .retrieve()
//                .bodyToFlux(UserVO.class)
//                .blockFirst();

        UserVO registeredUser = restTemplate.postForObject("http://user-service/users/auth", request, UserVO.class);

        String accessToken = jwtUtil.generate(registeredUser.getId(), registeredUser.getRole(), "ACCESS");
        String refreshToken = jwtUtil.generate(registeredUser.getId(), registeredUser.getRole(), "REFRESH");


        return new AuthResponse(accessToken, refreshToken);
    }

    @GetMapping(value = "/set-cookie")
    public ResponseEntity<?> setCookie(HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("data", "Come_to_the_dark_side");//создаем объект Cookie,
        //в конструкторе указываем значения для name и value
        cookie.setPath("/");//устанавливаем путь
        cookie.setMaxAge(86400);//здесь устанавливается время жизни куки
        response.addCookie(cookie);//добавляем Cookie в запрос
        response.setContentType("text/plain");//устанавливаем контекст
        return ResponseEntity.ok().body(HttpStatus.OK);//получилось как бы два раза статус ответа установили, выбирайте какой вариант лучше
    }

    public AuthResponse generateToken(AuthRequest request){

        return null;
    }

}
