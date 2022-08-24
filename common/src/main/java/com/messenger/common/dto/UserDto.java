package com.messenger.common.dto;




import javax.validation.constraints.Null;
import javax.validation.constraints.NotNull;
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
    private Set<UserDto> contactList;

    public interface UpdateContactList {
    }
//
//    public UserEntity toEntity() {
//        return new UserEntity(id, userName, lastActionDateTime, activeStatus, contactList);
//    }

    public String getUserName() {
        return userName;
    }
    public Set<UserDto> getContactList() {
        return contactList;
    }
}
