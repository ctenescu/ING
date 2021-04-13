package com.lucian.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = "EMAIL_IS_NULL_OR_EMPTY")
    private String email;

    @NotBlank(message = "PASSWORD_IS_EMPTY_OR_NULL")
    private String password;

    @Size(min = 2, max = 70, message = "FIRSTNAME_MUST_BE_BETWEEN_2_AND_70_CHARACTERS")
    @NotBlank(message = "FIRSTNAME_IS_NULL_OR_EMPTY")
    private String firstName;

    @Size(min = 2, max = 70, message = "LASTNAME_MUST_BE_BETWEEN_2_AND_70_CHARACTERS")
    @NotBlank(message = "LASTNAME_IS_NULL_OR_EMPTY")
    private String lastName;

    @NotNull(message = "FIELD_IS_ACTIVE_IS_NULL")
    private Boolean isActive;

    private Integer age;


}
