MERGE INTO users (id, username, email)
KEY (id)
VALUES (2000, 'user1', 'user1@example.com'), (2001, 'user2', 'user2@example.com'), (2003, 'user3', 'user3@example.com');

MERGE INTO cart (id, user_id, items_json, status)
KEY (id)
VALUES (2000, 2001, '[{"itemId":2000,"descricao":"Descrição do produto","productId":2000,"quantity":2,"precoTotal":100.00,"precoUnitario":50.00}]', 'FINALIZADO');

INSERT INTO itens (item_id, descricao, product_id, quantity, preco_total, preco_unitario, cart_id)
VALUES (2000, 'Descrição do produto', 2000, 10, 100.00, 10.00, 2000);
