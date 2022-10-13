package com.messenger.common.dto;


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



}
