DELETE FROM items;
ALTER TABLE items ALTER COLUMN id RESTART WITH 1;

DELETE FROM users;
ALTER TABLE users ALTER COLUMN id RESTART WITH 1;

DELETE FROM cities;
ALTER TABLE cities ALTER COLUMN id RESTART WITH 1;

DELETE FROM item_categories;
ALTER TABLE item_categories ALTER COLUMN id RESTART WITH 1;

DELETE FROM bookmarks;

INSERT INTO users (name, email, password, registration_date, hash, avatar, user_statuses_id, user_roles_id)
VALUES ('user1', 'email1@email.com', 'password1', '2022-04-02 21:13:47', 'FJDL238432fhjer23', '123.png', 2, 1);

INSERT INTO users (name, email, password, registration_date, hash, avatar, user_statuses_id, user_roles_id)
VALUES ('user2', 'email2@email.com', 'password2', '2022-04-02 21:13:47', 'FJDGjgje3o2423go', '321.png', 1, 1);

INSERT INTO cities (title)
VALUES ('city1');

INSERT INTO item_categories (title)
VALUES ('category1');

INSERT INTO items(title, price, description, contact, create_time, update_time, picture, item_categories_id, users_id, cities_id)
VALUES('title1', 123, 'description1', '@contact', '2022-04-02 21:13:47', '2022-04-02 21:13:47', 'picture1', 1, 1, 1);


INSERT INTO bookmarks(users_id, items_id)
VALUES (1, 1);
