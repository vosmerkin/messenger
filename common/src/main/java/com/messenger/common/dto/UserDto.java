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

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
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
