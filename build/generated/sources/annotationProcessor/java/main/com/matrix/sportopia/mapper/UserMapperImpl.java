package com.matrix.sportopia.mapper;

import com.matrix.sportopia.entities.User;
import com.matrix.sportopia.models.dto.request.UserRequestDto;
import com.matrix.sportopia.models.dto.response.UserResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-26T11:59:01+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.7.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public UserResponseDto toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId( user.getId() );
        userResponseDto.setName( user.getName() );
        userResponseDto.setSurname( user.getSurname() );
        userResponseDto.setEmail( user.getEmail() );
        userResponseDto.setPhoneNumber( user.getPhoneNumber() );
        userResponseDto.setBankAccount( user.getBankAccount() );
        userResponseDto.setStatus( user.getStatus() );
        userResponseDto.setPhotoPath( user.getPhotoPath() );

        return userResponseDto;
    }

    @Override
    public User toEntity(UserRequestDto userRequestDto) {
        if ( userRequestDto == null ) {
            return null;
        }

        User user = new User();

        user.setName( userRequestDto.getName() );
        user.setSurname( userRequestDto.getSurname() );
        user.setUsername( userRequestDto.getUsername() );
        user.setEmail( userRequestDto.getEmail() );
        user.setPhoneNumber( userRequestDto.getPhoneNumber() );
        user.setPassword( userRequestDto.getPassword() );
        user.setBankAccount( userRequestDto.getBankAccount() );
        user.setStatus( userRequestDto.getStatus() );

        return user;
    }

    @Override
    public void updateEntityFromRequest(UserRequestDto userRequestDto, User user) {
        if ( userRequestDto == null ) {
            return;
        }

        if ( userRequestDto.getName() != null ) {
            user.setName( userRequestDto.getName() );
        }
        if ( userRequestDto.getSurname() != null ) {
            user.setSurname( userRequestDto.getSurname() );
        }
        if ( userRequestDto.getUsername() != null ) {
            user.setUsername( userRequestDto.getUsername() );
        }
        if ( userRequestDto.getEmail() != null ) {
            user.setEmail( userRequestDto.getEmail() );
        }
        if ( userRequestDto.getPhoneNumber() != null ) {
            user.setPhoneNumber( userRequestDto.getPhoneNumber() );
        }
        if ( userRequestDto.getPassword() != null ) {
            user.setPassword( userRequestDto.getPassword() );
        }
        if ( userRequestDto.getBankAccount() != null ) {
            user.setBankAccount( userRequestDto.getBankAccount() );
        }
        if ( userRequestDto.getStatus() != null ) {
            user.setStatus( userRequestDto.getStatus() );
        }
    }
}
