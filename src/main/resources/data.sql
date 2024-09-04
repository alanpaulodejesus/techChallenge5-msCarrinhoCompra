
--INSERT INTO users (id, username, email) VALUES (12,'user12', 'user1@example.com');

MERGE INTO users (id, username, email)
KEY (id)
VALUES (2000, 'user1', 'user1@example.com'), (2001, 'user2', 'user2@example.com'), (2003, 'user3', 'user3@example.com');

MERGE INTO cart (id, user_id, items_json, status)
KEY (id)
VALUES (2000, 2001, '[{"productId":2000, "quantity":2}]', 'FINALIZADO');

INSERT INTO cart_item (product_id, quantity, cart_id) VALUES (2000, 10, 2000);