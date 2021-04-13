package com.lucian.mapper.user;

import com.lucian.domain.UserEntity;
import com.lucian.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper
public abstract class UserEntityToDTOMapper {
    @Mappings({
    })
    public abstract UserDTO mapUserEntityToDTO(UserEntity userEntity);
}
