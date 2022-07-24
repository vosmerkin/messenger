package com.messenger.backend.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tbl_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "active_date_time")
    private Date lastActionDateTime;

    @Column(name ="active")
    private Boolean activeStatus;

    @ManyToMany
    @JoinTable(
            name = "tbl_contacts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id"))
    Set<UserEntity> contactList;

    public UserEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
