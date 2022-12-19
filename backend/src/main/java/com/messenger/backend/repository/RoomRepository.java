package com.messenger.backend.repository;

import com.messenger.backend.entity.RoomEntity;
import com.messenger.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {

    RoomEntity findByRoomName(String roomName);

    RoomEntity findAllById(Integer id);

}
