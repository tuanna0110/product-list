# --- !Ups

insert into user(username, password, created_at, updated_at) VALUES('admin', SHA2('admin123', 512), NOW(), NOW());
insert into user(username, password, created_at, updated_at) VALUES('admin1', SHA2('admin123', 512), NOW(), NOW());

# --- !Downs

delete from user;
