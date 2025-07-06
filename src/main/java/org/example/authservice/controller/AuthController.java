package org.example.authservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.authservice.dto.AuthentificationResponseToken;
import org.example.authservice.dto.UserAuthDTO;
import org.example.authservice.dto.UserRegDTO;
import org.example.authservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final UserService userService;

    @PostMapping("/reg")
    public ResponseEntity<AuthentificationResponseToken> reg(@RequestBody UserRegDTO userRegDTO) {
        AuthentificationResponseToken authentificationResponseToken =
                userService.registrationUser(userRegDTO);

        return ResponseEntity.ok(authentificationResponseToken);
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthentificationResponseToken> auth (@RequestBody UserAuthDTO userAuthDTO) {
        AuthentificationResponseToken authentificationResponseToken =
                userService.authUser(userAuthDTO);

        return ResponseEntity.ok(authentificationResponseToken);
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthentificationResponseToken> refreshToken (
            HttpServletRequest request,
            HttpServletResponse response) {

      return ResponseEntity.ok(userService.refreshToken(response,request));
    }

}
