package com.lucian.service.logout;

import com.lucian.dto.Response;
import com.lucian.dto.UserLogoutRequest;
import org.springframework.http.ResponseEntity;

public interface LogoutService {
    ResponseEntity<Response> logout(UserLogoutRequest userLogoutRequest);
}
