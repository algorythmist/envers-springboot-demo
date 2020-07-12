create table customer (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(50),
    PRIMARY KEY (id)
);

create table customer_order (
    id INT NOT NULL AUTO_INCREMENT,
    customer_id INT NOT NULL,
    description VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    total_amount DECIMAL(35,22),
    PRIMARY KEY (id),
    CONSTRAINT FK_order_customer_id FOREIGN KEY (customer_id) REFERENCES customer (id)
);

create table order_item(
    id INT NOT NULL AUTO_INCREMENT,
    order_id INT NOT NULL,
    item_identifier VARCHAR(255) NOT NULL,
    price DECIMAL(35,22) NOT NULL,
    number INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_item_order_id FOREIGN KEY (order_id) REFERENCES customer_order (id)
);
