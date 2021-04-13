//package com.lucian.register;
//
//
//import com.lucian.domain.UserEntity;
//import com.lucian.dto.ResponseCreateUser;
//import com.lucian.dto.UserDTO;
//import com.lucian.dto.UserRegisterRequest;
//import com.lucian.mapper.user.UserDTOToEntityMapper;
//import com.lucian.mapper.user.UserEntityToDTOMapper;
//import com.lucian.repository.UserRepository;
//import com.lucian.service.register.impl.RegisterServiceImpl;
//import com.lucian.utils.SensitiveLog;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mapstruct.factory.Mappers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static com.lucian.utils.Constants.*;
//
//
//@RunWith(SpringRunner.class)
//public class RegisterServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    private UserDTOToEntityMapper userDTOToEntityMapper = Mappers.getMapper(UserDTOToEntityMapper.class);
//
//    private UserEntityToDTOMapper userEntityToDTOMapper = Mappers.getMapper(UserEntityToDTOMapper.class);
//
//    @Mock
//    SensitiveLog sensitiveLog;
//
//    @InjectMocks
//    private RegisterServiceImpl registerService = new RegisterServiceImpl();
//
//    @Test
//    public void createUserWhenUserIsValidReceiveOKTest() throws Exception {
//        UserRegisterRequest userRequest = new UserRegisterRequest();
//        userRequest.setEmail(EMAIL_TEST);
//        userRequest.setFirstName(FISRSTNAME_TEST);
//        userRequest.setLastName(LASTNAME_TEST);
//        userRequest.setIsActive(true);
//        userRequest.setPassword(TEST);
//        userRequest.setAge(10);
//
//        //UserEntity mapped
//        UserEntity userEntityMapped = userDTOToEntityMapper.mapUserDTOToEntity(userRequest);
//        //Mocking findByEmail method to return null
//        Mockito.when(this.userRepository.findByEmail(userRequest.getEmail())).thenReturn(null);
//        //Mocking call to save UserEntity in db
//        Mockito.when(userRepository.save(userEntityMapped)).thenReturn(userEntityMapped);
//        //Calling save UserEntity to db mocked
//        UserEntity userEntitySaved = userRepository.save(userEntityMapped);
//
//        //Test
//        assertThat(userEntityMapped).isNotNull();
//        Assert.assertEquals(userEntitySaved, userEntityMapped);
//
//
//
//        //Mocking call to save UserEntityDevice in db
//        Mockito.when(userDevicesRepository.save(userDevicesEntity)).thenReturn(userDevicesEntity);
//
//        UserDTO userDTO = userEntityToDTOMapper.mapUserEntityToDTO(userEntitySaved);
//
//
//
//        //CreateUserResponse mocked as ResponseEntity
//        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(new ResponseCreateUser(OK, userDTO));
//
//        //Call createUser method with Request given
//        ResponseEntity createUserResponse = registerService.createUser(userRequest);
//        //Test
//        Assert.assertEquals(createUserResponse, responseEntity);
//    }
////
////    @Test
////    public void shouldReturn500WhenUserExitsButItsNotActiveTest() throws Exception {
////        UserRequest userRequest = new UserRequest();
////        userRequest.setEmail(EMAIL_TEST);
////        userRequest.setFirstName(FISRSTNAME_TEST);
////        userRequest.setLastName(LASTNAME_TEST);
////        userRequest.setIsActive(true);
////        userRequest.setBusinessIdentifier(BID_TEST);
////        userRequest.setDevice(DEVICE_CODE_TEST);
////
////        //Mapping UserRequest to UserEntity
////        UserEntity userEntityMapped = userDTOToEntityMapper.mapUserDTOToEntity(userRequest);
////        //Set isActive == false for ALREADY_EXISTS_BUT_ITS_NOT_ACTIVE flow
////        userEntityMapped.setIsActive(false);
////        //Mocking findByEmail method
////        Mockito.when(this.userRepository.findByEmail(userRequest.getEmail().toLowerCase())).thenReturn(userEntityMapped);
////
////        //ResponseCreateUser mocked as ResponseEntity
////        ResponseEntity responseEntityMocked = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseCreateUser(USER_ALREADY_EXISTS_BUT_ITS_NOT_ACTIVE, null));
////
////        //Call createUser method with Request given
////        ResponseEntity createUserResponse = registerService.createUser(userRequest);
////
////        //Test
////        Assert.assertEquals(responseEntityMocked, createUserResponse);
////    }
////
////    @Test
////    public void shouldReturn500ConflictWhenUserExitsAndItsActiveTest() throws Exception {
////        UserRequest userRequest = new UserRequest();
////        userRequest.setEmail(EMAIL_TEST);
////        userRequest.setFirstName(FISRSTNAME_TEST);
////        userRequest.setLastName(LASTNAME_TEST);
////        userRequest.setIsActive(true);
////        userRequest.setBusinessIdentifier(BID_TEST);
////        userRequest.setDevice(DEVICE_CODE_TEST);
////
////        //Mapping UserRequest to UserEntity wich isActive == true and should be CONFLICT
////        UserEntity userEntityMapped = userDTOToEntityMapper.mapUserDTOToEntity(userRequest);
////        //Mocking findByEmail method
////        Mockito.when(this.userRepository.findByEmail(userRequest.getEmail().toLowerCase())).thenReturn(userEntityMapped);
////
////        //ResponseCreateUser mocked as ResponseEntity for Internal server error
////        ResponseEntity responseEntityMocked = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseCreateUser(CONFLICT, null));
////
////        //Call createUser method with Request given
////        ResponseEntity createUserResponse = registerService.createUser(userRequest);
////
////        //Test
////        Assert.assertEquals(responseEntityMocked, createUserResponse);
////    }
////
////    @Test
////    public void createUserInternalServerError() throws Exception {
////        UserRequest userRequest = new UserRequest();
////        userRequest.setEmail(EMAIL_TEST);
////        userRequest.setFirstName(FISRSTNAME_TEST);
////        userRequest.setLastName(LASTNAME_TEST);
////        userRequest.setIsActive(true);
////        userRequest.setBusinessIdentifier(BID_TEST);
////        userRequest.setDevice(DEVICE_CODE_TEST);
////
////        //UserEntity mapped
////        UserEntity userEntityMapped = userDTOToEntityMapper.mapUserDTOToEntity(userRequest);
////        //Mocking findByEmail method to return null
////        Mockito.when(this.userRepository.findByEmail(userRequest.getEmail()))
////                .thenThrow(new RuntimeException(INTERNAL_SERVER_ERROR_));
////
////        //CreateUserResponse mocked as ResponseEntity
////        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseCreateUser(INTERNAL_SERVER_ERROR, null));
////
////        //Call createUser method with Request given
////        ResponseEntity createUserResponse = registerService.createUser(userRequest);
////        //Test
////        Assert.assertEquals(createUserResponse, responseEntity);
////    }
//
//}
