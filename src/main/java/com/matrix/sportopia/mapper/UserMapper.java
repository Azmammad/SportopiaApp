package com.matrix.sportopia.mapper;

import com.matrix.sportopia.entities.User;
import com.matrix.sportopia.models.dto.request.UserRequestDto;
import com.matrix.sportopia.models.dto.response.UserResponseDto;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UserMapper {

    public abstract UserResponseDto toResponse(User user);

//    @Mapping(source = "photoPath", target = "photoPath", qualifiedByName = "mapToPhotoPath")
    public abstract User toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "id",ignore = true)
    public abstract void updateEntityFromRequest(UserRequestDto userRequestDto, @MappingTarget User user);

//    @Named(value = "mapToPhotoPath")
//    public String mapToPhotoPath(MultipartFile photoPath){
//        return null;
//    }

}
