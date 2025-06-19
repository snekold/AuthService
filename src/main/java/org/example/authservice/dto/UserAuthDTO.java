package org.example.authservice.dto;

import lombok.Builder;
import lombok.Data;
/**
 * DTO объект безопасной передачи данных юзера для авторизации
 * */
@Data
@Builder
public class UserAuthDTO {
    private String email;
    private String password;

}
