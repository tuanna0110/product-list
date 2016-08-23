# --- !Ups

create table product (
  id                            bigint auto_increment not null,
  title                         varchar(128) not null,
  description                   varchar(512) not null,
  price                         int not null,
  image_id                      varchar(256),
  created_at                    timestamp,
  updated_at                    timestamp,
  FULLTEXT (title, description),
  constraint product_pk primary key (id)
) ENGINE=MyISAM;

create table user (
  id                            bigint auto_increment not null,
  auth_token                    varchar(255),
  username                      varchar(255) not null,
  password                      varchar(512) not null,
  created_at                    timestamp,
  updated_at                    timestamp,
  constraint user_uq_email_address unique (username),
  constraint user_pk primary key (id)
);

create table image_id_counter (
  id                            bigint auto_increment not null,
  created_at                    timestamp,
  updated_at                    timestamp,
  constraint image_id_counter_pk primary key (id)
);

insert into user(username, password, created_at, updated_at) VALUES('test', SHA2('test123', 512), NOW(), NOW());

# --- !Downs

drop table if exists product;

drop table if exists user;

drop table if exists image_id_counter;
