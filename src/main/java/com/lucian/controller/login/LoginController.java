package com.lucian.controller.login;

import com.lucian.dto.Response;
import com.lucian.dto.UserLoginRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface LoginController {

    @PostMapping(value = "/login",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Response> login(@Valid @RequestBody UserLoginRequest userLoginRequest);

}
