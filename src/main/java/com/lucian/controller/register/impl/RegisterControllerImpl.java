package com.lucian.controller.register.impl;


import com.lucian.controller.register.RegisterController;
import com.lucian.dto.ResponseCreateUser;
import com.lucian.dto.UserRegisterRequest;
import com.lucian.service.register.RegisterService;
import com.lucian.utils.Constants;
import com.lucian.utils.SensitiveLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class RegisterControllerImpl implements RegisterController {
    private static final String CREATE_USER_METHOD = "createUser";

    private static final String CREATE_USER_PATH = "/createUser";

    @Autowired
    private SensitiveLog sensitiveLog;

    Logger logger = LoggerFactory.getLogger(RegisterControllerImpl.class);

    @Autowired
    RegisterService registerService;

    @Override
    public ResponseEntity<ResponseCreateUser> createUser(UserRegisterRequest userRegisterRequest) {
        Map<String, String> logIdentifiers = new HashMap<>();
        logIdentifiers.put(Constants.TYPE, Constants.INPUT);
        sensitiveLog.logSensitive("", logIdentifiers, CREATE_USER_PATH, CREATE_USER_METHOD, logger, null, null);

        ResponseEntity<ResponseCreateUser> response = registerService.createUser(userRegisterRequest);
        logIdentifiers.replace(Constants.TYPE, Constants.OUTPUT);
        sensitiveLog.logSensitive(response.getBody(), logIdentifiers, CREATE_USER_PATH, CREATE_USER_METHOD, logger, response.getBody().getMessageStatus(), response.getStatusCodeValue());
        return response;

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseCreateUser> handleValidationException(
            MethodArgumentNotValidException ex) {
        ResponseCreateUser response = new ResponseCreateUser();
        response.setPayload(null);
        response.setMessageStatus( ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.status(400).body(response);
    }
}
