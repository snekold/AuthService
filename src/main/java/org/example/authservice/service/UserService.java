package org.example.authservice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.authservice.dto.AuthentificationResponseToken;
import org.example.authservice.dto.UserAuthDTO;
import org.example.authservice.dto.UserRegDTO;
import org.example.authservice.entity.User;

public interface UserService {
    AuthentificationResponseToken registrationUser(UserRegDTO userRegDTO);
    User getUserById(long id);
    User getUserByName(String name);
    AuthentificationResponseToken authUser(UserAuthDTO userAuthDTO);
    AuthentificationResponseToken refreshToken(HttpServletResponse httpResponse, HttpServletRequest httpRequest);

}