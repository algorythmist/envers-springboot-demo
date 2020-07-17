CREATE TABLE revision_info (
    id INT NOT NULL AUTO_INCREMENT,
    revision_timestamp TIMESTAMP NOT NUll,
    username VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE customer_order_history (
    id INT,
    customer_id INT,
    description VARCHAR(255),
    status VARCHAR(50),
    total_amount DECIMAL(35,22),
    revision_id integer NOT NULL,
    revision_type INT,
    CONSTRAINT FK_customer_order_revision FOREIGN KEY (revision_id) REFERENCES revision_info (id)
);

CREATE TABLE order_item_history (
    id INT,
    order_id INT,
    item_identifier VARCHAR(255),
    price DECIMAL(35,22),
    number INT,
    revision_id integer NOT NULL,
    revision_type INT,
    CONSTRAINT FK_customer_item_revision FOREIGN KEY (revision_id) REFERENCES revision_info (id)
);

