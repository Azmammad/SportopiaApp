package com.matrix.sportopia.services.impl;

import com.matrix.sportopia.entities.User;
import com.matrix.sportopia.models.dto.response.UserResponseDto;
import com.matrix.sportopia.mapper.UserMapper;
import com.matrix.sportopia.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private User user;
    private UserResponseDto responseDto;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}