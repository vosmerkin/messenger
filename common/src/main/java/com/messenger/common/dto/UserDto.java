package com.messenger.common.dto;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.Objects;
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
    private Set<UserDto> contactList;

    public final static UserDto EMPTY_USER_DTO = new UserDto(0, null, null, null, null);

    public UserDto(@NotNull(groups = {UpdateContactList.class}) Integer id,
                   @Null(groups = {UpdateContactList.class}) String userName,
                   @Null(groups = {UpdateContactList.class}) Date lastActionDateTime,
                   @Null(groups = {UpdateContactList.class}) Boolean activeStatus,
                   @NotNull(groups = {UpdateContactList.class}) Set<UserDto> contactList) {
        this.id = id;
        this.userName = userName;
        this.lastActionDateTime = lastActionDateTime;
        this.activeStatus = activeStatus;
        this.contactList = contactList;
    }

    public UserDto(String json) {   // which way is better to convert to json?
        //constructor, accepting String json as a parameter, and creating UserDTO instance
        //or
        // static method
        var om = new ObjectMapper();
        UserDto userDto;
        try {
            userDto = om.readValue(json, UserDto.class);
        } catch (JsonProcessingException e) {
            userDto = EMPTY_USER_DTO;
            e.printStackTrace();
        }
        this.id = userDto.getId();
        this.userName=userDto.getUserName();
        this.activeStatus= userDto.getActiveStatus();
        this.lastActionDateTime=userDto.getLastActionDateTime();
        this.contactList=userDto.getContactList();
    }


    public interface UpdateContactList {
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

    public Date getLastActionDateTime() {
        return lastActionDateTime;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public Set<UserDto> getContactList() {
        return contactList;
    }

    @Override
    public boolean equals(Object aThat) {
        //a standard implementation pattern
        if (this == aThat) return true;
        if (!(aThat instanceof UserDto)) return false;
        UserDto that = (UserDto) aThat;
        for (int i = 0; i < this.getSigFields().length; ++i) {
            if (!Objects.equals(this.getSigFields()[i], that.getSigFields()[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        //simple one-line implementation
        return Objects.hash(getSigFields());
    }

    private Object[] getSigFields() {
        Object[] result = {id, userName, contactList};
        return result;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("id: ");
        s.append((id == null) ? "null" : id.toString());
        s.append(", userName: ");
        s.append((userName != null) ? userName.toString() : "null");
        s.append(", activeStatus: ");
        s.append((activeStatus) ? "online" : "offline");
        s.append(", lastActionDateTime: ");
        s.append((lastActionDateTime != null) ? lastActionDateTime.toString() : "null");
        s.append(", contactList: ");
        s.append((contactList != null) ? contactList.toString() : "null");
        return s.toString();
    }

    public String toJson() {
        var ow = new ObjectMapper();
        String json;
        try {
            json = ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            json = "{}";
            e.printStackTrace();
        }
        return json;
    }

    public static UserDto fromJson(String json) {
        var om = new ObjectMapper();
        UserDto userDto;
        try {
            userDto = om.readValue(json, UserDto.class);
        } catch (JsonProcessingException e) {
            userDto = EMPTY_USER_DTO;
            e.printStackTrace();
        }
        return userDto;
    }
}
