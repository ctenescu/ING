package com.lucian.controller.login.impl;

import com.lucian.controller.login.LoginController;
import com.lucian.dto.Response;
import com.lucian.dto.UserLoginRequest;
import com.lucian.service.login.LoginService;
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
public class LoginControllerImpl implements LoginController {

    private static final String LOGIN_USER_METHOD = "login";

    private static final String LOGIN_USER_PATH = "/login";

    @Autowired
    private SensitiveLog sensitiveLog;

    Logger logger = LoggerFactory.getLogger(LoginControllerImpl.class);

    @Autowired
    LoginService loginService;

    @Override
    public ResponseEntity<Response> login(UserLoginRequest userLoginRequest) {
        Map<String, String> logIdentifiers = new HashMap<>();
        logIdentifiers.put(Constants.TYPE, Constants.INPUT);
        sensitiveLog.logSensitive("", logIdentifiers, LOGIN_USER_PATH, LOGIN_USER_METHOD, logger, null, null);

        ResponseEntity<Response> response = loginService.login(userLoginRequest);
        logIdentifiers.replace(Constants.TYPE, Constants.OUTPUT);
        sensitiveLog.logSensitive(response.getBody(), logIdentifiers, LOGIN_USER_PATH, LOGIN_USER_METHOD, logger, response.getBody().getMessageStatus(), response.getStatusCodeValue());
        return response;
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationException(
            MethodArgumentNotValidException ex) {
        Response response = new Response();
        response.setMessageStatus(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.status(400).body(response);
    }
}
