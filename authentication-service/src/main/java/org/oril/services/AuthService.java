package org.oril.services;

import io.netty.handler.codec.http.HttpMethod;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.oril.entities.AuthRequest;
import org.oril.entities.AuthResponse;
import org.oril.entities.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public AuthResponse register(AuthRequest request) {
        //do validation if user exists in DB
        request.setPassword(
                BCrypt.hashpw(request.getPassword(), BCrypt.gensalt())
        );

        //
        //        UserVO registeredUser = WebClient.create("http://localhost:8080")
        //                .post()
        //                .uri("user-service/users")
        //                .bodyValue(request)
        //                .retrieve()
        //                .bodyToFlux(UserVO.class)
        //                .blockFirst();

        UserVO registeredUser = restTemplate.postForObject(
                "http://user-service/users/auth",
                request,
                UserVO.class
        );

        String accessToken = jwtUtil.generate(
                registeredUser.getId(),
                registeredUser.getRole(),
                "ACCESS"
        );
        String refreshToken = jwtUtil.generate(
                registeredUser.getId(),
                registeredUser.getRole(),
                "REFRESH"
        );

        return new AuthResponse(accessToken, refreshToken);
    }

    //    @GetMapping(value = "/set-cookie")
    //    public ResponseEntity<?> setCookie(HttpServletResponse response) throws IOException {
    //        Cookie cookie = new Cookie("data", "Come_to_the_dark_side");//создаем объект Cookie,
    //        //в конструкторе указываем значения для name и value
    //        cookie.setPath("/");//устанавливаем путь
    //        cookie.setMaxAge(86400);//здесь устанавливается время жизни куки
    //        response.addCookie(cookie);//добавляем Cookie в запрос
    //        response.setContentType("text/plain");//устанавливаем контекст
    //        return ResponseEntity.ok().body(HttpStatus.OK);//получилось как бы два раза статус ответа установили, выбирайте какой вариант лучше
    //    }

    public AuthResponse generateTokens(String refreshToken){
        String id = jwtUtil.getId(refreshToken);

        UserVO user = restTemplate.getForObject("http://user-service/users/is-expired/{id}", UserVO.class, id);


//        UserVO user = restTemplate.postForObject(
//                "http://user-service/users/is-expired",
//                id,
//                UserVO.class);

        if(user!= null){
            String accessToken = jwtUtil.generate(
                    user.getId(),
                    user.getRole(),
                    "ACCESS");
            String refreshTokenResult = jwtUtil.generate(
                    user.getId(),
                    user.getRole(),
                    "REFRESH");

            return new AuthResponse(accessToken, refreshTokenResult);
        }
        return null;
    }

}
