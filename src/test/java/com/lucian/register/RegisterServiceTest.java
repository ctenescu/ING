package com.lucian.register;


import com.lucian.domain.UserEntity;
import com.lucian.dto.ResponseCreateUser;
import com.lucian.dto.UserDTO;
import com.lucian.dto.UserRegisterRequest;
import com.lucian.mapper.user.UserDTOToEntityMapper;
import com.lucian.mapper.user.UserEntityToDTOMapper;
import com.lucian.repository.UserRepository;
import com.lucian.service.register.impl.RegisterServiceImpl;
import com.lucian.utils.SensitiveLog;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.lucian.utils.Constants.*;


@RunWith(SpringRunner.class)
public class RegisterServiceTest {

    public static final String INVALID_EMAIL_ADRESS = "INVALID_EMAIL_ADRESS";

    @Mock
    private UserRepository userRepository;

    private UserDTOToEntityMapper userDTOToEntityMapper = Mappers.getMapper(UserDTOToEntityMapper.class);

    private UserEntityToDTOMapper userEntityToDTOMapper = Mappers.getMapper(UserEntityToDTOMapper.class);

    @Mock
    SensitiveLog sensitiveLog;

    @InjectMocks
    private RegisterServiceImpl registerService = new RegisterServiceImpl();

    @Test
    public void createUserWhenUserIsValidReceiveOKTest() throws Exception {
        UserRegisterRequest userRequest = new UserRegisterRequest();
        userRequest.setEmail(EMAIL_TEST);
        userRequest.setFirstName(FISRSTNAME_TEST);
        userRequest.setLastName(LASTNAME_TEST);
        userRequest.setIsActive(true);
        userRequest.setPassword(TEST);
        userRequest.setAge(10);

        //UserEntity mapped
        UserEntity userEntityMapped = userDTOToEntityMapper.mapUserDTOToEntity(userRequest);

        //Mocking findByEmail method to return Optional.empty()
        Mockito.when(this.userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());

        //Mocking call to save UserEntity in db
        Mockito.when(userRepository.save(userEntityMapped)).thenReturn(userEntityMapped);

        //Calling save UserEntity to db mocked
        UserEntity userEntitySaved = userRepository.save(userEntityMapped);

        //Test
        Assert.assertEquals(userEntitySaved, userEntityMapped);

        UserDTO userDTO = userEntityToDTOMapper.mapUserEntityToDTO(userEntitySaved);

        //CreateUserResponse mocked as ResponseEntity
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(new ResponseCreateUser(SUCCESS, userDTO));

        //Call createUser method with Request given
        ResponseEntity createUserResponse = registerService.createUser(userRequest);
        //Test
        Assert.assertEquals(createUserResponse, responseEntity);
    }

    @Test
    public void conflictUserAlreadyExists() throws Exception {
        UserRegisterRequest userRequest = new UserRegisterRequest();
        userRequest.setEmail(EMAIL_TEST);
        userRequest.setFirstName(FISRSTNAME_TEST);
        userRequest.setLastName(LASTNAME_TEST);
        userRequest.setIsActive(true);
        userRequest.setPassword(TEST);
        userRequest.setAge(10);

        //UserEntity mapped
        UserEntity userEntityMapped = userDTOToEntityMapper.mapUserDTOToEntity(userRequest);

        //Mocking findByEmail
        Mockito.when(this.userRepository.findByEmail(userRequest.getEmail().toLowerCase())).thenReturn(Optional.ofNullable(userEntityMapped));

        //CreateUserResponse mocked as ResponseEntity
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseCreateUser(USER_ALREADY_EXISTS, null));

        //Call createUser method with Request given
        ResponseEntity createUserResponse = registerService.createUser(userRequest);
        //Test
        Assert.assertEquals(createUserResponse, responseEntity);
    }

    @Test
    public void badRequestInvalidEmailAdressTest() throws Exception {
        UserRegisterRequest userRequest = new UserRegisterRequest();
        userRequest.setEmail("EMAIL_TEST"); // for INVALID_EMAIL_ADRESS flow
        userRequest.setFirstName(FISRSTNAME_TEST);
        userRequest.setLastName(LASTNAME_TEST);
        userRequest.setIsActive(true);
        userRequest.setPassword(TEST);
        userRequest.setAge(10);

        //UserEntity mapped
        UserEntity userEntityMapped = userDTOToEntityMapper.mapUserDTOToEntity(userRequest);
        //Mocking findByEmail method to return null
        Mockito.when(this.userRepository.findByEmail(userRequest.getEmail().toLowerCase())).thenReturn(Optional.ofNullable(userEntityMapped));

        //CreateUserResponse mocked as ResponseEntity
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseCreateUser(INVALID_EMAIL_ADRESS, null));

        //Call createUser method with Request given
        ResponseEntity createUserResponse = registerService.createUser(userRequest);
        //Test
        Assert.assertEquals(createUserResponse, responseEntity);
    }


}
