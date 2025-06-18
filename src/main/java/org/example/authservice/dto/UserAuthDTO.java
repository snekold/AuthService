package org.example.authservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthDTO {
    private String email;
    private String password;

}
