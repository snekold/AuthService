package org.example.authservice.dto;

import lombok.Data;
import org.example.authservice.entity.Roles;
/**
 * DTO объект для безопасной передачи данных ( доп.инкапсуляция )
 * Не содержит методов, а только конструктор и свойства для дальнейшей реализации
 */
@Data
public class UserRegDTO {
    private String username;
    private String password;
    private Roles role;

}
