package com.messenger.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class UserDto implements Serializable {

    private Integer id;
    private String userName;
    private Boolean activeStatus;

    public UserDto() {
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
        return Objects.equals(id, userDto.id)
                && Objects.equals(userName, userDto.userName)
                && Objects.equals(activeStatus, userDto.activeStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, activeStatus);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", activeStatus=" + activeStatus +
                '}';
    }

}
