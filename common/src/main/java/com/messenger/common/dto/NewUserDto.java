package com.messenger.common.dto;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class NewUserDto implements Serializable {

    private Integer id;
    @NotNull
    private String userName;
    private Date lastActionDateTime;
    private Boolean activeStatus;
    private Set<UserDto> contactList;

    public NewUserDto() {
    }

    public NewUserDto(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewUserDto that = (NewUserDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(userName, that.userName)
                && Objects.equals(lastActionDateTime, that.lastActionDateTime)
                && Objects.equals(activeStatus, that.activeStatus)
                && Objects.equals(contactList, that.contactList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, lastActionDateTime, activeStatus, contactList);
    }

    @Override
    public String toString() {
        return "NewUserDto{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", lastActionDateTime=" + lastActionDateTime +
                ", activeStatus=" + activeStatus +
                ", contactList=" + contactList +
                '}';
    }

}
