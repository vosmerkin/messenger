package com.messenger.backend.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.Set;

public class UserDto {
    @NotNull(groups = {UpdateContactList.class})
    private Integer id;
    @Null(groups = {UpdateContactList.class})
    private String userName;
    @Null(groups = {UpdateContactList.class})
    private Date lastActionDateTime;
    @Null(groups = {UpdateContactList.class})
    private Boolean activeStatus;
    @NotNull(groups = {UpdateContactList.class})
    private Set<UserEntity> contactList;

    public interface UpdateContactList {
    }

    public UserEntity toEntity() {
        return new UserEntity(id, userName, lastActionDateTime, activeStatus, contactList);
    }
}
