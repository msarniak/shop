CREATE TABLE "order" (
    id BIGSERIAL PRIMARY KEY,
    buyer_email varchar(64) NOT NULL,
    order_date timestamp NOT NULL
);

CREATE INDEX order_date_idx ON "order" (order_date);