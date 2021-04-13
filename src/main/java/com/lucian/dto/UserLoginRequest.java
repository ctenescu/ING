package com.lucian.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@Data
@NoArgsConstructor
public class UserLoginRequest {
    @NotBlank(message = "EMAIL_IS_NULL_OR_EMPTY")
    private String email;

    @NotBlank(message = "PASSWORD_IS_EMPTY_OR_NULL")
    private String password;

}
