CREATE TABLE order_item (
    id BIGSERIAL PRIMARY KEY,
    order_id bigint REFERENCES "order" (id),
    product_id bigint REFERENCES product (id),
    quantity int NOT NULL,
    price decimal(16,2) NOT NULL
);

CREATE UNIQUE INDEX order_item_order_product_idx ON order_item (order_id, product_id);

CREATE INDEX order_item_product_idx ON order_item (product_id);