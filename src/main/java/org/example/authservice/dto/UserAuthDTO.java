package org.example.authservice.dto;

import lombok.Builder;
import lombok.Data;
/**
 * DTO объект безопасной передачи данных юзера для авторизации
 * @username - имя юзера
 * */
@Data
@Builder
public class UserAuthDTO {
    private String username;
    private String password;

}
