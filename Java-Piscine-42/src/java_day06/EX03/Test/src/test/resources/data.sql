DELETE FROM products;
INSERT INTO products (name, price) VALUES ('name1', 10000);
INSERT INTO products (name, price) VALUES ('name2', 0);
INSERT INTO products (name, price) VALUES ('name3', 50);
INSERT INTO products (name, price) VALUES ('name4', 15);
INSERT INTO products (name, price) VALUES ('name5', 200);

DELETE FROM users;
INSERT INTO users (login, password, status) VALUES ('login123', 'password123', false);
INSERT INTO users (login, password, status) VALUES ('admin', 'admin123', true);
INSERT INTO users (login, password, status) VALUES ('namelogin@main.ru', 'my_password_null', false);


-- INSERT INTO test.products (name, price)
-- VALUES ('name1', 1000),
--        ('name2', 200),
--        ('name3', 0),
--        ('name4', 115),
--        ('name5', 333);