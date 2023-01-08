package com.messenger.backend.services;

import com.messenger.backend.config.BackEndConfig;
import com.messenger.backend.entity.UserEntity;
import com.messenger.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    public static final Instant POINT_IN_TIME = OffsetDateTime.parse("2010-12-31T23:59:59Z").toInstant();
    public static final String USER_NAME = "TestUser";
    private static final Integer TEST_ID = 10;

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @BeforeEach
    void setUp() {
    }


    @Test
    void getByUserName() {
        // Arrange
        UserEntity testUser = new UserEntity(TEST_ID, USER_NAME, Date.from(POINT_IN_TIME), true, Collections.emptySet());
        when(repository.findByUserName(USER_NAME)).thenReturn(testUser);

        // Act
        final var result = service.getByUserName(USER_NAME);

        // Assert
        assertThat(result).isEqualTo(testUser);
        verify(repository).findByUserName(USER_NAME);
    }

    @Test
    void getByUserId() {
    }

    @Test
    void getUserStatus() {
    }

    @Test
    void createUser() {
    }

    @Test
    void updateUser() {
    }
}