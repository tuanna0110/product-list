# --- !Ups

create table product (
  id                            bigint auto_increment not null,
  title                         varchar(128) not null,
  description                   varchar(512) not null,
  price                         int not null,
  create_date                   timestamp not null,
  update_date                   timestamp not null,
  FULLTEXT (title, description),
  constraint product_pk primary key (id)
) ENGINE=MyISAM;

create table user (
  id                            bigint auto_increment not null,
  auth_token                    varchar(255),
  username                      varchar(255) not null,
  password                      varchar(512) not null,
  create_date                   timestamp not null,
  constraint user_uq_email_address unique (username),
  constraint user_pk primary key (id)
);

insert into user(username, password, create_date) VALUES('admin', SHA2('admin123', 512), NOW());

# --- !Downs

drop table if exists product;

drop table if exists user;
