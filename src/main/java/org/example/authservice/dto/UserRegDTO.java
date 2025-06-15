package org.example.authservice.dto;

import lombok.Data;
import org.example.authservice.entity.Roles;

@Data
public class UserRegDTO {
    private String username;
    private String password;
    private Roles role;

}
