package org.example.authservice.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.authservice.entity.Token;
import org.example.authservice.repo.TokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogoutHendlerImpl implements LogoutHandler {


    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String jwt = authorization.substring(7);
        Token token = tokenRepository.findByToken(jwt)
                .orElse(null);

        if (token == null) {
            return;
        }

        token.setExpired(true);
        token.setRevoked(true);
        tokenRepository.save(token);
        SecurityContextHolder.clearContext();
    }
}
