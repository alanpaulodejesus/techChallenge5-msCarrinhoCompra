
--INSERT INTO users (id, username, email) VALUES (12,'user12', 'user1@example.com');

MERGE INTO users (id, username, email)
KEY (id)
VALUES (1, 'user1', 'user1@example.com'), (2, 'user2', 'user2@example.com'), (3, 'user3', 'user3@example.com');

MERGE INTO cart (id, user_id, items_json, status)
KEY (id)
VALUES (1, 1, '[{"productId":1, "quantity":2}]', 'FINALIZADO');

INSERT INTO cart_item (product_id, quantity, cart_id) VALUES (1, 10, 1);