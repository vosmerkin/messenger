package com.messenger.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.backend.config.BackEndConfig;
import com.messenger.backend.entity.UserEntity;
import com.messenger.backend.services.UserService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@Import(BackEndConfig.class)
class UserControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final String TEST_USER = "test";
    public static final Instant POINT_IN_TIME = OffsetDateTime.parse("2010-12-31T23:59:59Z").toInstant();
    public static final String USER_NAME = "TestUser";

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

        when(userService.getByUserName(TEST_USER)).thenReturn(testUser);
        mockMvc.perform(get("/getUser")
                        .param("name", TEST_USER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(USER_NAME))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.activeStatus").value(true));

        verify(userService).getByUserName(TEST_USER);
    }

    @Test
    void getUserStatus() {
    }

    @Test
    void createUser() {
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