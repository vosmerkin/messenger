package com.messenger.common.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class UserDto implements Serializable {

    public static final UserDto EMPTY_ENTITY = new UserDto(-1, "DEFAULT USER", null, false, Collections.emptySet());
    private Integer id;
    private String userName;

    private Date lastActionDateTime;
    private Boolean activeStatus;
    private Set<UserDto> contactList;

    public UserDto() {
    }

    public UserDto(Integer id, String userName, Date lastActionDateTime, Boolean activeStatus, Set<UserDto> contactList) {
        this.id = id;
        this.userName = userName;
        this.lastActionDateTime = lastActionDateTime;
        this.activeStatus = activeStatus;
        this.contactList = contactList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(userName, userDto.userName) && Objects.equals(lastActionDateTime, userDto.lastActionDateTime) && Objects.equals(activeStatus, userDto.activeStatus) && Objects.equals(contactList, userDto.contactList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", lastActionDateTime=" + lastActionDateTime +
                ", activeStatus=" + activeStatus +
                '}';
    }
}
