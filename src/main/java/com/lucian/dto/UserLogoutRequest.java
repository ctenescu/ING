package com.lucian.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@Data
@NoArgsConstructor
public class UserLogoutRequest {
    @NotBlank(message = "EMAIL_IS_NULL_OR_EMPTY")
    private String email;
}
