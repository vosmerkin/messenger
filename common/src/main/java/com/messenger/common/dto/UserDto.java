package com.messenger.common.dto;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.Set;

public class UserDto {

    @Min(value = 0,groups = {EmptyUserDto.class})
    @Max(value = 0,groups = {EmptyUserDto.class})
    @NotNull(groups = {UpdateContactList.class})
    private Integer id;
    @Null(groups = {UpdateContactList.class,EmptyUserDto.class})
    private String userName;
    @Null(groups = {UpdateContactList.class,EmptyUserDto.class})
    private Date lastActionDateTime;


    @Null(groups = {UpdateContactList.class,EmptyUserDto.class})
    private Boolean activeStatus;
    @Null(groups = {EmptyUserDto.class})
    @NotNull(groups = {UpdateContactList.class})
    private Set<UserDto> contactList;

    public final static UserDto EMPTY_USER_DTO = new UserDto(0,null,null,null,null);
    public UserDto(@Min(value = 0,groups = {EmptyUserDto.class})
                   @Max(value = 0,groups = {EmptyUserDto.class})
                   @NotNull(groups = {UpdateContactList.class}) Integer id,
                   @Null(groups = {UpdateContactList.class,EmptyUserDto.class}) String userName,
                   @Null(groups = {UpdateContactList.class, EmptyUserDto.class}) Date lastActionDateTime,
                   @Null(groups = {UpdateContactList.class, EmptyUserDto.class}) Boolean activeStatus,
                   @Null(groups = {EmptyUserDto.class})
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

    public interface EmptyUserDto {
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
