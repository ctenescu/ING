package com.lucian.service.logout.impl;

import com.lucian.domain.UserEntity;
import com.lucian.dto.Response;
import com.lucian.dto.UserLogoutRequest;
import com.lucian.repository.UserRepository;
import com.lucian.service.logout.LogoutService;
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
public class LogoutServiceImpl implements LogoutService {
    public static final String INVALID_EMAIL_ADRESS = "INVALID_EMAIL_ADRESS";
    public static final String EMAIL_IS_WRONG = "EMAIL_IS_WRONG";
    public static final String USER_LOGOUT_REQUEST = "UserLogoutRequest";

    Logger logger = LoggerFactory.getLogger(LogoutServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    private SensitiveLog sensitiveLog;


    @Override
    public ResponseEntity<Response> logout(UserLogoutRequest userLogoutRequest) {
        ResponseEntity<Response> responseEntity = null;
        Map<String, String> logIdentifiers = new HashMap<>();
        logIdentifiers.put(Constants.TYPE, Constants.INPUT);

        try {
            logIdentifiers.put(Constants.EMAIL, userLogoutRequest.getEmail());
            sensitiveLog.logSensitive(USER_LOGOUT_REQUEST, logIdentifiers, null, null, logger, null, null);

            boolean isEmailValid = EmailValidator.checkEmail(userLogoutRequest.getEmail());
            if (!isEmailValid) {
                sensitiveLog.logSensitive(USER_LOGOUT_REQUEST, logIdentifiers, null, null, logger, INVALID_EMAIL_ADRESS, 400);
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(INVALID_EMAIL_ADRESS));

            } else {
                Optional<List<UserEntity>> listOfUsers = Optional.of(userRepository.findAll());
                if (listOfUsers.isPresent()) {
                    for (UserEntity userEntity : listOfUsers.get()) {
                        if (userEntity.getEmail().equalsIgnoreCase(userLogoutRequest.getEmail())) {
                            userEntity.setIsActive(false);
                            userRepository.save(userEntity);
                            responseEntity = ResponseEntity.status(HttpStatus.OK).body(new Response(Constants.SUCCESS));
                        } else {
                            responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(EMAIL_IS_WRONG));
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
