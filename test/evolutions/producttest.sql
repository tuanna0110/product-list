# --- !Ups

insert into product(title, description, price, created_at, updated_at) VALUES('テスト', 'テスト', 100, NOW(), NOW());
insert into product(title, description, price, created_at, updated_at) VALUES('テスト　テスト', 'テスト　テスト', 200, NOW(), NOW());
insert into product(title, description, price, created_at, updated_at) VALUES('テスト　テスト　テスト', 'テスト　テスト　テスト', 300, NOW(), NOW());
insert into product(title, description, price, created_at, updated_at) VALUES('テスト　テスト　テスト　テスト', 'テスト　テスト　テスト　テスト', 400, NOW(), NOW());
insert into product(title, description, price, created_at, updated_at) VALUES('テスト　テスト　テスト　テスト　テスト', 'テスト　テスト　テスト　テスト　テスト', 500, NOW(), NOW());
insert into product(title, description, price, created_at, updated_at) VALUES('テスト　テスト　テスト　テスト　テスト　テスト', 'テスト　テスト　テスト　テスト　テスト　テスト', 600, NOW(), NOW());
insert into product(title, description, price, created_at, updated_at) VALUES('テスト　テスト　テスト　テスト　テスト　テスト　テスト', 'テスト　テスト　テスト　テスト　テスト　テスト　テスト', 700, NOW(), NOW());

# --- !Downs

delete from product;