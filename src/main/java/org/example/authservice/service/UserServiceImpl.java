package org.example.authservice.service;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.authservice.dto.AuthentificationResponseToken;
import org.example.authservice.dto.UserAuthDTO;
import org.example.authservice.dto.UserRegDTO;
import org.example.authservice.entity.Roles;
import org.example.authservice.entity.Token;
import org.example.authservice.entity.User;
import org.example.authservice.repo.TokenRepository;
import org.example.authservice.repo.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private TokenRepository tokenRepository;
    private AuthenticationManager authenticationManager;

    @Override
    public AuthentificationResponseToken registrationUser(UserRegDTO userRegDTO) {
        User user = User.builder()
                .password(passwordEncoder.encode(userRegDTO.getPassword()))
                .role(userRegDTO.getRole())
                .username(userRegDTO.getUsername())
                .build();

        User userSaved = userRepository.save(user);
        String jwtToken = jwtService.generateToken(userSaved);
        String refreshToken = jwtService.generateRefreshToken(userSaved);

        saveUserToken(userSaved, jwtToken);

        return AuthentificationResponseToken.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();

    }

    public AuthentificationResponseToken authUser(UserAuthDTO userAuthDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userAuthDTO.getUsername(),
                userAuthDTO.getPassword()));

        User user = userRepository.findByUsername(userAuthDTO.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserToken(user);

        Token tokenJwt = Token.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(tokenJwt);

        return AuthentificationResponseToken.builder()
                .accessToken(tokenJwt.getToken())
                .refreshToken(refreshToken)
                .build();

    }


    public void revokeAllUserToken(User user) {
        var validsTokens = tokenRepository.findAllValidTokensByUserId(user.getId());
        if (validsTokens.isEmpty()) {
            return;
        }
        validsTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });

        tokenRepository.saveAll(validsTokens);

    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByUsername(name).orElse(null);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User with id " + id + " not found"));
    }


    public AuthentificationResponseToken refreshUsertoken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String username;

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalStateException("Refresh token is missing");
        }

        refreshToken = authorizationHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);

        if (username != null) {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalStateException("User not found"));

            if (jwtService.isValidToken(refreshToken, user)) {
                String token = jwtService.generateToken(user);
                revokeAllUserToken(user);
                saveUserToken(user, token);

                return AuthentificationResponseToken.builder()
                        .accessToken(token)
                        .refreshToken(refreshToken)
                        .build();
            }

        }
        throw  new IllegalStateException("Invalid refresh token");
    }

    private void saveUserToken(User user, String token) {
        Token tokenJwt = Token.builder()
                .user(user)
                .token(token)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(tokenJwt);
    }


}
