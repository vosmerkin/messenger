package com.messenger.backend.services;

import com.messenger.backend.config.BackEndConfig;
import com.messenger.backend.entity.UserEntity;
import com.messenger.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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


    @DisplayName("Testing UserService/getByUserName")
    @Test
    void getByUserName() {
        UserEntity testUser = new UserEntity(TEST_ID, USER_NAME, Date.from(POINT_IN_TIME), true, Collections.emptySet());
        when(repository.findByUserName(USER_NAME)).thenReturn(testUser);

        final var result = service.getByUserName(USER_NAME);

        assertThat(result).isEqualTo(testUser);
        verify(repository).findByUserName(USER_NAME);
    }

    @DisplayName("Testing UserService/getByUserName in case of Null userName argument")
    @Test
    void getByUserNameNullArgument() {
        when(repository.findByUserName(null)).thenReturn(null);

        final var result = service.getByUserName(null);

        assertThat(result).isEqualTo(UserEntity.EMPTY_ENTITY);
        verify(repository).findByUserName(null);
    }

    @DisplayName("Testing UserService/getByUserName in case of empty userName argument")
    @Test
    void getByUserNameEmptyStringArgument() {
        String emptyArgument = "";
        when(repository.findByUserName(emptyArgument)).thenReturn(null);

        final var result = service.getByUserName(emptyArgument);

        assertThat(result).isEqualTo(UserEntity.EMPTY_ENTITY);
        verify(repository).findByUserName(emptyArgument);
    }

    @DisplayName("Testing UserService/getByUserName in case of not existing userName argument")
    @Test
    void getByUserNameNotExistingUser() {
        when(repository.findByUserName(USER_NAME)).thenReturn(null);

        final var result = service.getByUserName(USER_NAME);

        assertThat(result).isEqualTo(UserEntity.EMPTY_ENTITY);
        verify(repository).findByUserName(USER_NAME);
    }

    @DisplayName("Testing UserService/getByUserId")
    @Test
    void getByUserId() {
        UserEntity testUser = new UserEntity(TEST_ID, USER_NAME, Date.from(POINT_IN_TIME), true, Collections.emptySet());
        when(repository.getReferenceById(TEST_ID)).thenReturn(testUser);

        final var result = service.getByUserId(TEST_ID);

        assertThat(result).isEqualTo(testUser);
        verify(repository).getReferenceById(TEST_ID);
    }

    @DisplayName("Testing UserService/getByUserId in case of Null id argument")
    @Test
    void getByUserIdNullArgument() {
        when(repository.getReferenceById(null)).thenReturn(null);

        final var result = service.getByUserId(null);

        assertThat(result).isEqualTo(UserEntity.EMPTY_ENTITY);
        verify(repository).getReferenceById(null);
    }

    @DisplayName("Testing UserService/getByUserId in case of not existing user id")
    @Test
    void getByUserIdNotExistingUserId() {
        when(repository.getReferenceById(TEST_ID)).thenReturn(UserEntity.EMPTY_ENTITY);

        final var result = service.getByUserId(TEST_ID);

        assertThat(result).isEqualTo(UserEntity.EMPTY_ENTITY);
        verify(repository).getReferenceById(TEST_ID);
    }

    @DisplayName("Testing UserService/createUser")
    @Test
    void createUser() {
        UserEntity testUser = new UserEntity(TEST_ID, USER_NAME, Date.from(POINT_IN_TIME), true, Collections.emptySet());
        when(repository.findByUserName(USER_NAME)).thenReturn(null);
        when(repository.save(ArgumentMatchers.any())).thenReturn(testUser);

        final var result = service.createUser(USER_NAME);

        assertThat(result).isEqualTo(testUser);
        verify(repository).findByUserName(USER_NAME);
        verify(repository).save(ArgumentMatchers.any());
    }

    @DisplayName("Testing UserService/createUser in case of Null userName argument")
    @Test
    void createUserNullArgument() {
        final var result = service.createUser(null);

        assertThat(result).isEqualTo(UserEntity.EMPTY_ENTITY);
    }

    @DisplayName("Testing UserService/createUser in case of empty userName argument")
    @Test
    void createUserEmptyStringArgument() {
        final var result = service.createUser("");

        assertThat(result).isEqualTo(UserEntity.EMPTY_ENTITY);
    }

    @DisplayName("Testing UserService/createUser in case of existing userName")
    @Test
    void createUserExistingUser() {
        UserEntity testUser = new UserEntity(TEST_ID, USER_NAME, Date.from(POINT_IN_TIME), true, Collections.emptySet());
        when(repository.findByUserName(USER_NAME)).thenReturn(testUser);

        final var result = service.createUser(USER_NAME);

        assertThat(result).isEqualTo(UserEntity.EMPTY_ENTITY);
        verify(repository).findByUserName(USER_NAME);
    }

    @DisplayName("Testing UserService/updateUser")
    @Test
    void updateUser() {
        UserEntity testUser = new UserEntity(TEST_ID, USER_NAME, Date.from(POINT_IN_TIME), true, Collections.emptySet());
        when(repository.existsById(TEST_ID)).thenReturn(true);
        when(repository.getReferenceById(TEST_ID)).thenReturn(testUser);
        when(repository.save(ArgumentMatchers.any())).thenReturn(testUser);

        final var result = service.updateUser(testUser);

        assertThat(result).isEqualTo(testUser);
        verify(repository).existsById(TEST_ID);
        verify(repository).getReferenceById(TEST_ID);
        verify(repository).save(ArgumentMatchers.any());
    }

    @DisplayName("Testing UserService/updateUser in case of Null user argument")
    @Test
    void updateUserNullArgument() {
        final var result = service.updateUser(null);

        assertThat(result).isEqualTo(UserEntity.EMPTY_ENTITY);
    }

    @DisplayName("Testing UserService/updateUser in case of not existing user")
    @Test
    void updateUserNotExistingUser() {
        UserEntity testUser = new UserEntity(TEST_ID, USER_NAME, Date.from(POINT_IN_TIME), true, Collections.emptySet());
        when(repository.existsById(TEST_ID)).thenReturn(false);

        final var result = service.updateUser(testUser);

        assertThat(result).isEqualTo(UserEntity.EMPTY_ENTITY);
        verify(repository).existsById(TEST_ID);
    }


}