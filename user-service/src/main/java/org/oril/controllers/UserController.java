package org.oril.controllers;

import lombok.AllArgsConstructor;
import org.oril.entities.user.Dto.AuthRequest;
import org.oril.entities.user.Dto.UserVO;
import org.oril.exception.UserFoundException;
import org.oril.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/auth")
    public ResponseEntity save(@RequestBody AuthRequest authRequest) throws UserFoundException {
        if(userService.notExists(authRequest)){
            return ResponseEntity.ok(userService.save(authRequest));
        }

        throw new UserFoundException("Дублирующее имя");
    }

    @GetMapping("/secured")
    public ResponseEntity<String> securedEndpoint() {
        return ResponseEntity.ok("Hello, from secured endpoint!");
    }
}
