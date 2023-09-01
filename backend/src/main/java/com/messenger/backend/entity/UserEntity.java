package com.messenger.backend.entity;

import com.google.protobuf.Timestamp;
import grpc_generated.UserProto;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_users")
public class UserEntity {

    public static final UserEntity EMPTY_ENTITY = new UserEntity(-1, "DEFAULT USER", null, false,
            Collections.emptySet());
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "active_date_time")
    private Date lastActionDateTime;

    @Column(name = "active")
    private Boolean activeStatus;

    @ManyToMany
    @JoinTable(
            name = "tbl_contacts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private Set<UserEntity> contactList;

    @ManyToMany(mappedBy = "roomUsers")
    Set<RoomEntity> rooms;

    public UserEntity() {
    }

    public UserEntity(Integer id, String userName, Date lastActionDateTime, Boolean activeStatus,
                      Set<UserEntity> contactList) {
        this.id = id;
        this.userName = userName;
        this.lastActionDateTime = lastActionDateTime;
        this.activeStatus = activeStatus;
        this.contactList = contactList;
    }

    public UserEntity(Integer id) {
        this.id = id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLastActionDateTime() {
        return lastActionDateTime;
    }

    public void setLastActionDateTime(Date lastActionDateTime) {
        this.lastActionDateTime = lastActionDateTime;
    }


    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Set<RoomEntity> getRooms() {
        return rooms;
    }

    public void setRooms(Set<RoomEntity> rooms) {
        this.rooms = rooms;
    }

    public Set<UserEntity> getContactList() {
        return contactList;
    }

    public void setContactList(Set<UserEntity> contactList) {
        this.contactList = contactList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(userName, that.userName) && Objects.equals(lastActionDateTime, that.lastActionDateTime) && Objects.equals(activeStatus, that.activeStatus) && Objects.equals(contactList, that.contactList) && Objects.equals(rooms, that.rooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, lastActionDateTime, activeStatus, contactList, rooms);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", lastActionDateTime=" + lastActionDateTime +
                ", activeStatus=" + activeStatus +
                ", contactList=" + contactList.size() + " contacts" +
                '}';
    }

    public static UserProto toProto(UserEntity user) {
        if (user == null) return null;
        return UserProto.newBuilder()
                .setUserId(user.getId())
                .setUserName(user.getUserName())
                .setLastActionDateTime(Timestamp.newBuilder()
                        .setSeconds(user.getLastActionDateTime().toInstant().getEpochSecond())
                        .setNanos(user.getLastActionDateTime().toInstant().getNano())
                        .build())
                .setActiveStatus(user.getActiveStatus())
                .addAllContactList(new HashSet<>(user.getContactList().stream().map(UserEntity::toProto).collect(Collectors.toSet()))).build();
    }
}
