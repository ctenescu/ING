package com.lucian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCreateUser {

    private String messageStatus;
    private UserDTO payload;


}
