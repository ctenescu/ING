package com.lucian.service.register.impl;


import com.lucian.domain.UserEntity;
import com.lucian.dto.ResponseCreateUser;
import com.lucian.dto.UserDTO;
import com.lucian.dto.UserRegisterRequest;
import com.lucian.mapper.user.UserDTOToEntityMapper;
import com.lucian.mapper.user.UserEntityToDTOMapper;
import com.lucian.repository.UserRepository;
import com.lucian.service.register.RegisterService;
import com.lucian.utils.Constants;
import com.lucian.utils.EmailValidator;
import com.lucian.utils.SensitiveLog;
import liquibase.util.StringUtils;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RegisterServiceImpl implements RegisterService {


    private static final String FIRST_NAME = "First name ";

    private static final String LAST_NAME = "Last name ";

    private static final String USER_REQUEST = "UserRegisterRequest  : ";

    public static final String IS_ACTIVE = "Is active";

    public static final String INVALID_EMAIL_ADRESS = "INVALID_EMAIL_ADRESS";

    @Autowired
    UserRepository userRepository;

    @Autowired
    private SensitiveLog sensitiveLog;

    Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    private UserEntityToDTOMapper userEntityToDTOMapper = Mappers.getMapper(UserEntityToDTOMapper.class);
    private UserDTOToEntityMapper userRequesToEntityMapper = Mappers.getMapper(UserDTOToEntityMapper.class);

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseCreateUser> createUser(UserRegisterRequest userRegisterRequest) {
        ResponseEntity<ResponseCreateUser> responseEntity;
        Map<String, String> logIdentifiers = new HashMap<>();
        logIdentifiers.put(Constants.TYPE, Constants.INPUT);

        try {
            logIdentifiers.put(Constants.EMAIL, userRegisterRequest.getEmail());
            logIdentifiers.put(FIRST_NAME, userRegisterRequest.getFirstName());
            logIdentifiers.put(LAST_NAME, userRegisterRequest.getLastName());
            logIdentifiers.put(IS_ACTIVE, userRegisterRequest.getIsActive().toString());
            logIdentifiers.put("PASWORD", userRegisterRequest.getPassword());
            logIdentifiers.put("AGE", userRegisterRequest.getAge().toString());
            sensitiveLog.logSensitive(USER_REQUEST, logIdentifiers, null, null, logger, null, null);

            // check email
            if (!StringUtils.isEmpty(userRegisterRequest.getEmail())) {

                boolean isEmailValid = EmailValidator.checkEmail(userRegisterRequest.getEmail());
                if (!isEmailValid) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseCreateUser(INVALID_EMAIL_ADRESS, null));
                }
            }

            Optional<UserEntity> userEntity = userRepository.findByEmail(userRegisterRequest.getEmail().toLowerCase());
            if (userEntity.isPresent()) {
                if (userEntity.get().getIsActive() == false) {
                    responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseCreateUser(Constants.USER_ALREADY_EXISTS_BUT_ITS_NOT_ACTIVE, null));
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseCreateUser(Constants.USER_ALREADY_EXISTS, null));
                }
            } else {
                UserEntity userEntityToSave = userRequesToEntityMapper.mapUserDTOToEntity(userRegisterRequest);

                UserEntity userEntitySaved = userRepository.save(userEntityToSave);

                UserDTO userDTO = userEntityToDTOMapper.mapUserEntityToDTO(userEntitySaved);

                responseEntity = ResponseEntity.status(HttpStatus.OK).body(new ResponseCreateUser(Constants.SUCCESS, userDTO));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logIdentifiers.replace(Constants.TYPE, Constants.ERROR);
            sensitiveLog.logSensitive(Constants.ERROR_MESSAGE + e.getMessage(), logIdentifiers, null, null, logger, null, null);
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseCreateUser(Constants.INTERNAL_SERVER_ERROR, null));
        }
        logIdentifiers.replace(Constants.TYPE, Constants.OUTPUT);
        sensitiveLog.logSensitive(responseEntity.getBody(), logIdentifiers, null, null, logger, responseEntity.getBody().getMessageStatus(), responseEntity.getStatusCodeValue());
        return responseEntity;
    }

}
