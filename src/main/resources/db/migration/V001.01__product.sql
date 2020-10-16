CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name varchar(64) NOT NULL,
    price decimal(16,2) NOT NULL,
    CONSTRAINT product_uniqe_name UNIQUE (name)
);