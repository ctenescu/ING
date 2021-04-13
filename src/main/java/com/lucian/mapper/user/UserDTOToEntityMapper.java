package com.lucian.mapper.user;


import com.lucian.domain.UserEntity;
import com.lucian.dto.UserRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper
public abstract class UserDTOToEntityMapper {
    @Mappings({
    })
    public abstract UserEntity mapUserDTOToEntity(UserRegisterRequest userRegisterRequest);
}



