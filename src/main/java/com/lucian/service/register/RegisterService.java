package com.lucian.service.register;

import com.lucian.dto.ResponseCreateUser;
import com.lucian.dto.UserRegisterRequest;
import org.springframework.http.ResponseEntity;

public interface RegisterService {
    ResponseEntity<ResponseCreateUser> createUser(UserRegisterRequest userRegisterRequest);
}
