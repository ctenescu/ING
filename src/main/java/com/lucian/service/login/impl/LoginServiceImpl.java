package com.lucian.service.login.impl;

import com.lucian.domain.UserEntity;
import com.lucian.dto.Response;
import com.lucian.dto.UserLoginRequest;
import com.lucian.repository.UserRepository;
import com.lucian.service.login.LoginService;
import com.lucian.utils.Constants;
import com.lucian.utils.EmailValidator;
import com.lucian.utils.SensitiveLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    public static final String INVALID_EMAIL_ADRESS = "INVALID_EMAIL_ADRESS";
    public static final String PASSWORD = "PASSWORD";
    public static final String PASSWORD_OR_EMAIL_IS_WRONG = "PASSWORD_OR_EMAIL_IS_WRONG";
    public static final String USER_LOGIN_REQUEST = "UserLoginRequest";

    Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    private SensitiveLog sensitiveLog;

    @Override
    public ResponseEntity<Response> login(UserLoginRequest userLoginRequest) {
        ResponseEntity<Response> responseEntity = null;
        Map<String, String> logIdentifiers = new HashMap<>();
        logIdentifiers.put(Constants.TYPE, Constants.INPUT);

        try {
            logIdentifiers.put(Constants.EMAIL, userLoginRequest.getEmail());
            logIdentifiers.put(PASSWORD, userLoginRequest.getPassword());

            sensitiveLog.logSensitive(USER_LOGIN_REQUEST, logIdentifiers, null, null, logger, null, null);

            boolean isEmailValid = EmailValidator.checkEmail(userLoginRequest.getEmail());
            if (!isEmailValid) {
                sensitiveLog.logSensitive(USER_LOGIN_REQUEST, logIdentifiers, null, null, logger, INVALID_EMAIL_ADRESS, 400);
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(INVALID_EMAIL_ADRESS));

            } else {
                Optional<List<UserEntity>> listOfUsers = Optional.of(userRepository.findAll());
                if (listOfUsers.isPresent()) {
                    for (UserEntity userEntity : listOfUsers.get()) {
                        if (userEntity.getEmail().equalsIgnoreCase(userLoginRequest.getEmail()) && userEntity.getPassword().equals(userLoginRequest.getPassword())) {
                            userEntity.setIsActive(true);
                            userRepository.save(userEntity);
                            responseEntity = ResponseEntity.status(HttpStatus.OK).body(new Response(Constants.SUCCESS));
                        } else {
                            responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(PASSWORD_OR_EMAIL_IS_WRONG));
                        }
                    }
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(Constants.INTERNAL_SERVER_ERROR));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logIdentifiers.replace(Constants.TYPE, Constants.ERROR);
            sensitiveLog.logSensitive(Constants.ERROR_MESSAGE + e.getMessage(), logIdentifiers, null, null, logger, e.getMessage(), 500);
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(e.getMessage()));
        }
        logIdentifiers.replace(Constants.TYPE, Constants.OUTPUT);
        sensitiveLog.logSensitive(responseEntity.getBody(), logIdentifiers, null, null, logger, responseEntity.getBody().getMessageStatus(), responseEntity.getStatusCodeValue());

        return responseEntity;
    }
}
