package com.lucian.controller.logout;

import com.lucian.dto.Response;
import com.lucian.dto.UserLogoutRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface LogoutController {
    @PostMapping(value = "/logout",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Response> logout(@Valid @RequestBody UserLogoutRequest userLogoutRequest);
}
