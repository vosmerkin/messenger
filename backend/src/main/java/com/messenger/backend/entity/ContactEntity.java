package com.messenger.backend.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name ="tbl_contacts")
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(mappedBy = "contactList")
    private Set<UserEntity> userEntitySet;

}
