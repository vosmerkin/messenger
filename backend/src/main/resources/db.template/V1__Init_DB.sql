create table tbl_contacts (
    user_id int4 not null,
    contact_id int4 not null,
    primary key (user_id, contact_id)
    );

create table tbl_msg (
    id  serial not null,
    date_time timestamp,
    msg_text varchar(255),
    room_id int4,
    user_id int4,
    primary key (id)
    );

 create table tbl_room (
    id  serial not null,
    room_name varchar(255),
    users json,
    primary key (id)
    );

 create table tbl_users (
    id  serial not null,
    active boolean,
    active_date_time timestamp,
    user_name varchar(255),
    primary key (id)
    );

 alter table tbl_contacts
    add constraint user_contacts_fk
    foreign key (contact_id) references tbl_users;

 alter table tbl_contacts
    add constraint user_in_tbl_contacts_fk
    foreign key (user_id) references tbl_users;

 alter table if exists tbl_msg
    add constraint FK2wu3y6b75y51ey9my0ndh7lae
    foreign key (room_id) references tbl_room;

 alter table tbl_msg
    add constraint FK38iryoiplomfo322ok7mwi3ow
    foreign key (user_id) references tbl_users;