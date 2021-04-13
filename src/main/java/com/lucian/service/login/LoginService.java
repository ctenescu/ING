package com.lucian.service.login;

import com.lucian.dto.Response;
import com.lucian.dto.UserLoginRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity<Response> login(UserLoginRequest userLoginRequest);
}
