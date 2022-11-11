create table tbl_contacts (
    id serial not null,
    user_id int4 not null,
    contact_id int4 not null,
    primary key (user_id, contact_id)
    );

create table tbl_msg (
    id serial not null,
    date_time timestamp,
    msg_text varchar(255),
    room_id int4,
    user_id int4,
    primary key (id)
    );

 create table tbl_room (
    id serial not null,
    room_name varchar(255),
    primary key (id)
    );

 create table tbl_users (
    id serial not null,
    active boolean,
    active_date_time timestamp,
    user_name varchar(255),
    primary key (id)
    );

 create table tbl_room_users (
    room_id int4 not null,
    user_id int4 not null,
    primary key (room_id, user_id));

 alter table tbl_contacts
    add constraint user_contacts_fk
    foreign key (contact_id) references tbl_users;

 alter table tbl_contacts
    add constraint user_in_tbl_contacts_fk
    foreign key (user_id) references tbl_users;

 alter table if exists tbl_msg
    add constraint room_msges_fk
    foreign key (room_id) references tbl_room;

 alter table tbl_msg
    add constraint msg_author_fk
    foreign key (user_id) references tbl_users;