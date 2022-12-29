package com.messenger.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.backend.config.BackEndConfig;
import com.messenger.backend.entity.UserEntity;
import com.messenger.backend.services.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@Import(BackEndConfig.class)
class UserControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final String TEST_USER = "test";
    public static final Instant POINT_IN_TIME = OffsetDateTime.parse("2010-12-31T23:59:59Z").toInstant();
    public static final String USER_NAME = "TestUser";
    private static final Integer TEST_ID = 10;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getNotExistedUser() throws Exception {
        when(userService.getByUserName(TEST_USER)).thenReturn(UserEntity.EMPTY_ENTITY);
        mockMvc.perform(get("/getUser")
                        .param("name", TEST_USER))
                .andExpect(status().isNotFound());

        verify(userService).getByUserName(TEST_USER);
    }

    @Test
    void getExistedUser() throws Exception {
        var testUser = new UserEntity(10, USER_NAME, Date.from(POINT_IN_TIME), true, Collections.emptySet());

        when(userService.getByUserName(TEST_USER)).thenReturn(testUser);      //setting behaviour of mocked UserService object
        mockMvc.perform(get("/getUser")
                        .param("name", TEST_USER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(USER_NAME))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.activeStatus").value(true));

        verify(userService).getByUserName(TEST_USER);    //verifying that business logic was called
    }

    @Test
    void getExistingActiveUserStatus() throws Exception {
        var testUser = new UserEntity(10, USER_NAME, Date.from(POINT_IN_TIME), true, Collections.emptySet());

        when(userService.getByUserId(TEST_ID)).thenReturn(testUser);

        mockMvc.perform(get("/getUserStatus")
                        .param("id", String.valueOf(TEST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("true"));

        verify(userService).getByUserId(TEST_ID);
    }

    @Test
    void getExistingNotActiveUserStatus() throws Exception {
        var testUser = new UserEntity(10, USER_NAME, Date.from(POINT_IN_TIME), false, Collections.emptySet());

        when(userService.getByUserId(TEST_ID)).thenReturn(testUser);

        mockMvc.perform(get("/getUserStatus")
                        .param("id", String.valueOf(TEST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("false"));

        verify(userService).getByUserId(TEST_ID);
    }

    @Test
    void getNotExistingUserStatus() throws Exception {
        when(userService.getByUserId(TEST_ID)).thenReturn(UserEntity.EMPTY_ENTITY);

        mockMvc.perform(get("/getUserStatus")
                        .param("id", String.valueOf(TEST_ID)))
                .andExpect(status().isNotFound());

        verify(userService).getByUserId(TEST_ID);
    }

    @Test
    void createUser() throws Exception{
        UserEntity newUser = new UserEntity(1,TEST_USER,Date.from(POINT_IN_TIME),true,Collections.emptySet());
        when(userService.createUser(TEST_USER)).thenReturn(newUser);

        mockMvc.perform(get("/createUser")
                        .param("name", String.valueOf(TEST_USER)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value(TEST_USER))
                .andExpect(jsonPath("$.id", Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.activeStatus").value(true));
        verify(userService).createUser(TEST_USER);
    }

    @Test
    void createUserEmptyName() throws Exception{
        when(userService.createUser("")).thenReturn(UserEntity.EMPTY_ENTITY);

        mockMvc.perform(get("/createUser")
                        .param("name", String.valueOf("")))
                .andExpect(status().isBadRequest());
//        verify(userService).createUser("");
    }

    @Test
    void createUserExistingUserName() throws Exception{
        UserEntity newUser = new UserEntity(1,TEST_USER,Date.from(POINT_IN_TIME),true,Collections.emptySet());
        when(userService.createUser(TEST_USER)).thenReturn(UserEntity.EMPTY_ENTITY);

        mockMvc.perform(get("/createUser")
                        .param("name", String.valueOf(TEST_USER)))
                .andExpect(status().isBadRequest());
        verify(userService).createUser(TEST_USER);
    }


//    @Test
//    void updateUser() throws Exception {
//        UserDto userDto = new UserDto();
//        String bodyStr = MAPPER.writeValueAsString(userDto);
//
//
//        verify(userService, never()).updateUserStatus(any());
//    }
}