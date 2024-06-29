package com.matrix.sportopia.mapper;

import com.matrix.sportopia.entities.User;
import com.matrix.sportopia.models.dto.request.UserRequestDto;
import com.matrix.sportopia.models.dto.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UserMapper {

    public abstract UserResponseDto toResponse(User user);

    public abstract User toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "id",ignore = true)
    public abstract void updateEntityFromRequest(UserRequestDto userRequestDto, @MappingTarget User user);


}
