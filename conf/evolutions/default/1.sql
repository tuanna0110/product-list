# --- !Ups

create table product (
  id                            bigint auto_increment not null,
  title                         varchar(128) not null,
  description                   varchar(512) not null,
  price                         int not null,
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

# --- !Downs

drop table if exists product;

drop table if exists user;
