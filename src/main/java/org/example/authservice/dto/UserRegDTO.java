package org.example.authservice.dto;

import lombok.Data;
import org.example.authservice.entity.Roles;
/**
 * DTO объект безопасной передачи данных юзера для его регистрации
 * Не содержит методов, а только конструктор и свойства для дальнейшей реализации
 */
@Data
public class UserRegDTO {
    private String username;
    private String password;
    private Roles role;

}
