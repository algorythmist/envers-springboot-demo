CREATE TABLE customer (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(50),
    created TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE customer_order (
    id INT NOT NULL AUTO_INCREMENT,
    customer_id INT NOT NULL,
    description VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    total_amount DECIMAL(35,22),
    created TIMESTAMP NOT NULL,
    updated TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_order_customer_id FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE order_item(
    id INT NOT NULL AUTO_INCREMENT,
    order_id INT NOT NULL,
    item_identifier VARCHAR(255) NOT NULL,
    price DECIMAL(35,22) NOT NULL,
    number INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_item_order_id FOREIGN KEY (order_id) REFERENCES customer_order (id)
);
