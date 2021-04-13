package com.lucian.controller.register;


import com.lucian.dto.ResponseCreateUser;
import com.lucian.dto.UserRegisterRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


public interface RegisterController {

    @PostMapping(value = "/createUser",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ResponseCreateUser> createUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest);

}
