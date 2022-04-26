create table if not exists cities
(
    id    int auto_increment
        primary key,
    title varchar(45) not null
);

create table if not exists item_categories
(
    id    int auto_increment
        primary key,
    title varchar(45) not null,
    unique (id)
);

create table if not exists user_roles
(
    id    int auto_increment
        primary key,
    title varchar(15) not null,
    unique (id)
);

create table if not exists user_statuses
(
    id    int auto_increment
        primary key,
    title varchar(15) not null,
    unique (id)
);

create table if not exists users
(
    id                int auto_increment
        primary key,
    name              varchar(45)  not null,
    email             varchar(45)  not null,
    password          varchar(255) not null,
    registration_date datetime     not null,
    hash              varchar(255) not null,
    avatar            varchar(80)  not null,
    user_statuses_id  int          not null,
    user_roles_id     int          not null,
    unique (id),
    constraint fk_users_user_roles1
        foreign key (user_roles_id) references user_roles (id),
    constraint fk_users_user_statuses1
        foreign key (user_statuses_id) references user_statuses (id)
);

create table if not exists items
(
    id                 int auto_increment
        primary key,
    title              varchar(45) not null,
    price              int         not null,
    description        text        not null,
    contact            varchar(45) not null,
    create_time        datetime    not null,
    update_time        datetime    not null,
    picture            varchar(80) not null,
    item_categories_id int         not null,
    users_id           int         not null,
    cities_id          int         not null,
    unique (id),
    constraint fk_items_cities1
        foreign key (cities_id) references cities (id),
    constraint fk_items_item_categories1
        foreign key (item_categories_id) references item_categories (id),
    constraint fk_items_users1
        foreign key (users_id) references users (id)
            on update cascade on delete cascade
);

create table if not exists bookmarks
(
    users_id int not null,
    items_id int not null,
    primary key (users_id, items_id),
    constraint fk_users_has_items_items1
        foreign key (items_id) references items (id)
            on update cascade on delete cascade,
    constraint fk_users_has_items_users1
        foreign key (users_id) references users (id)
            on update cascade on delete cascade
);

create index fk_users_has_items_items1_idx
    on bookmarks (items_id);

create index fk_users_has_items_users1_idx
    on bookmarks (users_id);

create table if not exists comments
(
    id          int auto_increment
        primary key,
    text        text     not null,
    create_time datetime not null,
    items_id    int      not null,
    users_id    int      not null,
    unique (id),
    constraint fk_comments_items1
        foreign key (items_id) references items (id)
            on update cascade on delete cascade,
    constraint fk_comments_users1
        foreign key (users_id) references users (id)
            on update cascade on delete cascade
);

create index fk_comments_items1_idx
    on comments (items_id);

create index fk_comments_users1_idx
    on comments (users_id);

create index fk_items_cities1_idx
    on items (cities_id);

create index fk_items_item_categories1_idx
    on items (item_categories_id);

create index fk_items_users1_idx
    on items (users_id);

create index fk_users_user_roles1_idx
    on users (user_roles_id);

create index fk_users_user_statuses1_idx
    on users (user_statuses_id);

INSERT INTO user_roles (title)
VALUES ('USER');
INSERT INTO user_roles (title)
VALUES ('ADMIN');
INSERT INTO user_roles (title)
VALUES ('MODER');

INSERT INTO user_statuses (title)
VALUES ('ACTIVATED');
INSERT INTO user_statuses (title)
VALUES ('INACTIVATED');
INSERT INTO user_statuses (title)
VALUES ('BANNED');