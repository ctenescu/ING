package com.lucian.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    private Integer userId;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Boolean isActive;

    private Integer age;


}
