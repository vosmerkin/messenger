package com.messenger.backend.services;

import com.messenger.backend.entity.RoomEntity;
import com.messenger.backend.entity.UserEntity;
import com.messenger.backend.repository.RoomRepository;
import com.messenger.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
    public static final String ROOM_NAME = "TestRoom";
    private static final Integer TEST_ID = 10;
    @InjectMocks
    private RoomService service;

    @Mock
    private RoomRepository repository;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("Testing RoomService/getByRoomName")
    @Test
    void getByRoomName() {
        RoomEntity testRoom = new RoomEntity(TEST_ID, ROOM_NAME, Collections.emptySet());
        when(repository.findByRoomName(ROOM_NAME)).thenReturn(testRoom);

        final var result = service.getByRoomName(ROOM_NAME);

        assertThat(result).isEqualTo(testRoom);
        verify(repository).findByRoomName(ROOM_NAME);
    }

    @DisplayName("Testing RoomService/getByRoomName in case of Null roomName argument")
    @Test
    void getByRoomNameNullArgument() {
        when(repository.findByRoomName(null)).thenReturn(null);

        final var result = service.getByRoomName(null);

        assertThat(result).isEqualTo(RoomEntity.EMPTY_ENTITY);
        verify(repository).findByRoomName(null);
    }

    @DisplayName("Testing RoomService/getByRoomName in case of empty roomName argument")
    @Test
    void getByRoomNameEmptyStringArgument() {
        String emptyArgument = "";
        when(repository.findByRoomName(emptyArgument)).thenReturn(null);

        final var result = service.getByRoomName(emptyArgument);

        assertThat(result).isEqualTo(RoomEntity.EMPTY_ENTITY);
        verify(repository).findByRoomName(emptyArgument);
    }

    @DisplayName("Testing RoomService/getByRoomName in case of not existing roomName")
    @Test
    void getByRoomNameNotExistingRoom() {
        when(repository.findByRoomName(ROOM_NAME)).thenReturn(null);

        final var result = service.getByRoomName(ROOM_NAME);

        assertThat(result).isEqualTo(RoomEntity.EMPTY_ENTITY);
        verify(repository).findByRoomName(ROOM_NAME);
    }

    @DisplayName("Testing RoomService/createRoom")
    @Test
    void createRoom() {
        RoomEntity testRoom = new RoomEntity(TEST_ID, ROOM_NAME, Collections.emptySet());
        when(repository.findByRoomName(ROOM_NAME)).thenReturn(null);
        when(repository.save(ArgumentMatchers.any())).thenReturn(testRoom);

        final var result = service.createRoom(ROOM_NAME);

        assertThat(result).isEqualTo(testRoom);
        verify(repository).findByRoomName(ROOM_NAME);
        verify(repository).save(ArgumentMatchers.any());
    }

    @DisplayName("Testing RoomService/createRoom in case of Null roomName argument")
    @Test
    void createRoomNullArgument() {
        final var result = service.createRoom(null);

        assertThat(result).isEqualTo(RoomEntity.EMPTY_ENTITY);
    }

    @DisplayName("Testing RoomService/createRoom in case of empty roomName argument")
    @Test
    void createRoomEmptyArgument() {
        String emptyArgument = "";
        final var result = service.createRoom(emptyArgument);

        assertThat(result).isEqualTo(RoomEntity.EMPTY_ENTITY);
    }

    @DisplayName("Testing RoomService/createRoom in case of existing roomName")
    @Test
    void createRoomExistingRoom() {
        RoomEntity testRoom = new RoomEntity(TEST_ID, ROOM_NAME, Collections.emptySet());
        when(repository.findByRoomName(ROOM_NAME)).thenReturn(testRoom);

        final var result = service.createRoom(ROOM_NAME);

        assertThat(result).isEqualTo(RoomEntity.EMPTY_ENTITY);
        verify(repository).findByRoomName(ROOM_NAME);
    }

    @DisplayName("Testing RoomService/updateRoom")
    @Test
    void updateRoom() {
        RoomEntity testRoom = new RoomEntity(TEST_ID, ROOM_NAME, Collections.emptySet());
        when(repository.existsById(TEST_ID)).thenReturn(true);
        when(repository.getReferenceById(TEST_ID)).thenReturn(testRoom);
        when(repository.save(ArgumentMatchers.any())).thenReturn(testRoom);

        final var result = service.updateRoom(testRoom);

        assertThat(result).isEqualTo(testRoom);
        verify(repository).existsById(TEST_ID);
        verify(repository).getReferenceById(TEST_ID);
        verify(repository).save(ArgumentMatchers.any());
    }

    @DisplayName("Testing RoomService/updateRoom in case of Null room argument")
    @Test
    void updateRoomNullArgument() {
        final var result = service.updateRoom(null);

        assertThat(result).isEqualTo(RoomEntity.EMPTY_ENTITY);
    }

    @DisplayName("Testing RoomService/updateRoom in case of not existing room")
    @Test
    void updateRoomExistingRoom() {
        RoomEntity testRoom = new RoomEntity(TEST_ID, ROOM_NAME, Collections.emptySet());
        when(repository.existsById(TEST_ID)).thenReturn(false);

        final var result = service.updateRoom(testRoom);

        assertThat(result).isEqualTo(RoomEntity.EMPTY_ENTITY);
        verify(repository).existsById(TEST_ID);
    }

}