package com.messenger.backend.repository;

import com.messenger.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepository extends JpaRepository<UserEntity, Integer> {
}
