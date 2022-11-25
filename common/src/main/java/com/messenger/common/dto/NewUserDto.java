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
    public boolean equals(Object aThat) {
        //a standard implementation pattern
        if (this == aThat) return true;
        if (!(aThat instanceof NewUserDto)) return false;
        NewUserDto that = (NewUserDto) aThat;
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
