package com.messenger.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.backend.config.BackEndConfig;
import com.messenger.backend.entity.UserEntity;
import com.messenger.backend.services.UserService;
import com.messenger.common.dto.JsonMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@Import(BackEndConfig.class)
class UserControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
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
        when(userService.getByUserName(USER_NAME)).thenReturn(UserEntity.EMPTY_ENTITY);
        mockMvc.perform(get("/getUser")
                        .param("name", USER_NAME))
                .andExpect(status().isNotFound());

        verify(userService).getByUserName(USER_NAME);
    }

    @Test
    void getExistedUser() throws Exception {
        UserEntity testUser = new UserEntity(TEST_ID, USER_NAME, Date.from(POINT_IN_TIME), true, Collections.emptySet());
        when(userService.getByUserName(USER_NAME)).thenReturn(testUser);      //setting behaviour of mocked UserService object
        mockMvc.perform(get("/getUser")
                        .param("name", USER_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(USER_NAME))
                .andExpect(jsonPath("$.id").value(TEST_ID))
                .andExpect(jsonPath("$.activeStatus").value(true));

        verify(userService).getByUserName(USER_NAME);    //verifying that business logic was called
    }

    @Test
    void getExistingActiveUserStatus() throws Exception {
        UserEntity activeTestUser = new UserEntity(TEST_ID, USER_NAME, Date.from(POINT_IN_TIME), true, Collections.emptySet());
        when(userService.getByUserId(TEST_ID)).thenReturn(activeTestUser);
        mockMvc.perform(get("/getUserStatus")
                        .param("id", String.valueOf(TEST_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("true"));

        verify(userService).getByUserId(TEST_ID);
    }

    @Test
    void getExistingNotActiveUserStatus() throws Exception {
        UserEntity notActiveTestUser = new UserEntity(TEST_ID, USER_NAME, Date.from(POINT_IN_TIME), false, Collections.emptySet());
        notActiveTestUser.setActiveStatus(false);

        when(userService.getByUserId(TEST_ID)).thenReturn(notActiveTestUser);

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
    void createUser() throws Exception {
        UserEntity testUser = new UserEntity(TEST_ID, USER_NAME, Date.from(POINT_IN_TIME), true, Collections.emptySet());
        when(userService.createUser(USER_NAME)).thenReturn(testUser);

        mockMvc.perform(get("/createUser")
                        .param("name", String.valueOf(USER_NAME)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value(USER_NAME))
                .andExpect(jsonPath("$.id", Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.activeStatus").value(true));
        verify(userService).createUser(USER_NAME);
    }

    @Test
    void createUserEmptyName() throws Exception {
        when(userService.createUser("")).thenReturn(UserEntity.EMPTY_ENTITY);

        mockMvc.perform(get("/createUser")
                        .param("name", String.valueOf("")))
                .andExpect(status().isBadRequest());
//        verify(userService).createUser("");
    }

    @Test
    void createUserExistingUserName() throws Exception {
        when(userService.createUser(USER_NAME)).thenReturn(UserEntity.EMPTY_ENTITY);

        mockMvc.perform(get("/createUser")
                        .param("name", String.valueOf(USER_NAME)))
                .andExpect(status().isBadRequest());
        verify(userService).createUser(USER_NAME);
    }

    @Test
    void updateUserName() throws Exception {
        UserEntity testUser = new UserEntity(TEST_ID, USER_NAME, Date.from(POINT_IN_TIME), true, Collections.emptySet());
        String newUserName = "TestUser1";
        testUser.setUserName(newUserName);
        when(userService.updateUser(ArgumentMatchers.any())).thenReturn(testUser);

        mockMvc.perform(put("/updateUser")
                        .contentType("application/json")
//                        .characterEncoding("utf-8")
                        .content(JsonMapper.toJson(testUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName", Matchers.equalTo(newUserName)));

        verify(userService).updateUser(ArgumentMatchers.any());
    }

    @Test
    void updateUserStatus() throws Exception {
        UserEntity testUser = new UserEntity(TEST_ID, USER_NAME, Date.from(POINT_IN_TIME), false, Collections.emptySet());
        when(userService.updateUser(ArgumentMatchers.any())).thenReturn(testUser);

        mockMvc.perform(put("/updateUser")
                        .contentType("application/json")
//                        .characterEncoding("utf-8")
                        .content(JsonMapper.toJson(testUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeStatus", Matchers.equalTo(false)));

        verify(userService).updateUser(ArgumentMatchers.any());
    }

    @Test
    void updateUserNotExisting() throws Exception {
        UserEntity testUser = new UserEntity(TEST_ID, USER_NAME, Date.from(POINT_IN_TIME), false, Collections.emptySet());
        when(userService.updateUser(ArgumentMatchers.any())).thenReturn(testUser);

        mockMvc.perform(put("/updateUser")
                        .contentType("application/json")
//                        .characterEncoding("utf-8")
                        .content(JsonMapper.toJson(testUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeStatus", Matchers.equalTo(false)));

        verify(userService).updateUser(ArgumentMatchers.any());
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