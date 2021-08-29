CREATE SCHEMA shopping_cart;

CREATE TABLE shopping_cart."order" (
	order_id bigserial NOT NULL,
	created_by varchar(255) NOT NULL,
	created_on date NOT NULL,
	order_status varchar(255) NOT NULL,
	customer_id int8 NOT NULL,
	CONSTRAINT order_pkey PRIMARY KEY (order_id)
);

CREATE TABLE shopping_cart.order_product (
	order_product_id bigserial NOT NULL,
	product_id int8 NOT NULL,
	quantity int4 NOT NULL,
	order_id int8 NOT NULL,
	CONSTRAINT order_product_pkey PRIMARY KEY (order_product_id)
);

ALTER TABLE shopping_cart.order_product ADD CONSTRAINT fkb3sm2umdh0yd7jwrnda0qixd0 FOREIGN KEY (order_id) REFERENCES shopping_cart."order"(order_id);


CREATE TABLE shopping_cart.product (
	product_id bigserial NOT NULL,
	product_available bool NOT NULL,
	product_name varchar(255) NOT NULL,
	product_price float8 NOT NULL,
	CONSTRAINT product_pkey PRIMARY KEY (product_id)
);


CREATE TABLE shopping_cart.inventory (
	item_id bigserial NOT NULL,
	created_by varchar(255) NOT NULL,
	created_on date NOT NULL,
	item_description varchar(255) NULL,
	quantity int4 NULL,
	updated_by varchar(255) NOT NULL,
	updated_on date NOT NULL,
	CONSTRAINT inventory_pkey PRIMARY KEY (item_id)
);

CREATE TABLE shopping_cart.payment (
	payment_id bigserial NOT NULL,
	created_by varchar(255) NOT NULL,
	created_on date NOT NULL,
	customer_id int8 NOT NULL,
	order_id int8 NOT NULL,
	total_order_price float8 NOT NULL,
	CONSTRAINT payment_pkey PRIMARY KEY (payment_id)
);

CREATE TABLE shopping_cart."transaction" (
	transaction_id bigserial NOT NULL,
	amount float8 NOT NULL,
	description varchar(255) NULL,
	invoice_number int8 NOT NULL,
	payment_id int8 NOT NULL,
	CONSTRAINT transaction_pkey PRIMARY KEY (transaction_id),
	CONSTRAINT fkq9m7rb5uydysanp8smxcovxlh FOREIGN KEY (payment_id) REFERENCES shopping_cart.payment(payment_id)
);
