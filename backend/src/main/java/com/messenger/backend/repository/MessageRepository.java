package com.messenger.backend.repository;

import com.messenger.backend.entity.MessageEntity;
import com.messenger.backend.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
    List<MessageEntity> getByRoomId(Integer id);
}
