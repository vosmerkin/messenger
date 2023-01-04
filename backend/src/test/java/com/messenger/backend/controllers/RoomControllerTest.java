package com.messenger.backend.controllers;

import com.messenger.backend.config.BackEndConfig;
import com.messenger.backend.entity.RoomEntity;
import com.messenger.backend.entity.RoomEntity;
import com.messenger.backend.entity.RoomEntity;
import com.messenger.backend.entity.RoomEntity;
import com.messenger.backend.services.RoomService;
import com.messenger.backend.services.RoomService;
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

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RoomController.class)
@Import(BackEndConfig.class)
class RoomControllerTest {
    public static final String ROOM_NAME = "TestRoom";
    private static final Integer TEST_ID = 10;
    public static final RoomEntity TEST_ROOM = new RoomEntity(TEST_ID, ROOM_NAME, Collections.emptySet());
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RoomService roomService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getNotExistedRoom() throws Exception {
        when(roomService.getByRoomName(ROOM_NAME)).thenReturn(RoomEntity.EMPTY_ENTITY);
        mockMvc.perform(get("/getRoom")
                        .param("name", ROOM_NAME))
                .andExpect(status().isNotFound());

        verify(roomService).getByRoomName(ROOM_NAME);
    }

    @Test
    void getExistedRoom() throws Exception {
        when(roomService.getByRoomName(ROOM_NAME)).thenReturn(TEST_ROOM);      //setting behaviour of mocked RoomService object
        mockMvc.perform(get("/getRoom")
                        .param("name", ROOM_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName").value(ROOM_NAME))
                .andExpect(jsonPath("$.id").value(TEST_ID));

        verify(roomService).getByRoomName(ROOM_NAME);    //verifying that business logic was called
    }

    @Test
    void createRoom() throws Exception {
        when(roomService.createRoom(ROOM_NAME)).thenReturn(TEST_ROOM);

        mockMvc.perform(get("/createRoom")
                        .param("name", String.valueOf(ROOM_NAME)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomName").value(ROOM_NAME))
                .andExpect(jsonPath("$.id", Matchers.greaterThan(0)));
        verify(roomService).createRoom(ROOM_NAME);
    }

    @Test
    void createRoomEmptyName() throws Exception {
        when(roomService.createRoom("")).thenReturn(RoomEntity.EMPTY_ENTITY);

        mockMvc.perform(get("/createRoom")
                        .param("name", String.valueOf("")))
                .andExpect(status().isBadRequest());
//        verify(roomService).createRoom("");
    }

    @Test
    void createRoomExistingRoomName() throws Exception {
        when(roomService.createRoom(ROOM_NAME)).thenReturn(RoomEntity.EMPTY_ENTITY);

        mockMvc.perform(get("/createRoom")
                        .param("name", String.valueOf(ROOM_NAME)))
                .andExpect(status().isBadRequest());
        verify(roomService).createRoom(ROOM_NAME);
    }

    @Test
    void updateRoomName() throws Exception {
        RoomEntity changedRoom = TEST_ROOM;
        when(roomService.updateRoom(ArgumentMatchers.any())).thenReturn(changedRoom);

        mockMvc.perform(put("/updateRoom")
                        .contentType("application/json")
//                        .characterEncoding("utf-8")
                        .content(JsonMapper.toJson(changedRoom))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName", Matchers.equalTo(ROOM_NAME)));

        verify(roomService).updateRoom(ArgumentMatchers.any());
    }
}