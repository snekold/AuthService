package org.example.authservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.authservice.dto.AuthentificationResponseToken;
import org.example.authservice.dto.UserRegDTO;
import org.example.authservice.entity.Token;
import org.example.authservice.entity.User;
import org.example.authservice.repo.TokenRepository;
import org.example.authservice.repo.UserRepository;
import org.example.authservice.service.JwtService;
import org.example.authservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    private final JwtService jwtService;
   // private final TokenRepository tokenRepository;
    private final UserService userService;

    @GetMapping("/lk")
    public ResponseEntity<User> lk(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);

            String userNameByToken = jwtService.extractUsername(token);
            User userByName = userService.getUserByName(userNameByToken);
            return ResponseEntity.ok(userByName);

        }
        return ResponseEntity.notFound().build();
    }
}
