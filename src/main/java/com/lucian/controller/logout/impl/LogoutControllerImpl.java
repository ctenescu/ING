package com.lucian.controller.logout.impl;

import com.lucian.controller.logout.LogoutController;
import com.lucian.dto.Response;
import com.lucian.dto.UserLogoutRequest;
import com.lucian.service.logout.LogoutService;
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

@RestController
@ControllerAdvice
public class LogoutControllerImpl implements LogoutController {
    private static final String LOGOUT_USER_METHOD = "logout";

    private static final String LOGOUT_USER_PATH = "/logout";

    @Autowired
    private SensitiveLog sensitiveLog;

    Logger logger = LoggerFactory.getLogger(LogoutControllerImpl.class);

    @Autowired
    LogoutService logoutService;


    @Override
    public ResponseEntity<Response> logout(UserLogoutRequest userLogoutRequest) {
        Map<String, String> logIdentifiers = new HashMap<>();
        logIdentifiers.put(Constants.TYPE, Constants.INPUT);
        sensitiveLog.logSensitive("", logIdentifiers, LOGOUT_USER_PATH, LOGOUT_USER_METHOD, logger, null, null);

        ResponseEntity<Response> response = logoutService.logout(userLogoutRequest);
        logIdentifiers.replace(Constants.TYPE, Constants.OUTPUT);
        sensitiveLog.logSensitive(response.getBody(), logIdentifiers, LOGOUT_USER_PATH, LOGOUT_USER_METHOD, logger, response.getBody().getMessageStatus(), response.getStatusCodeValue());
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
