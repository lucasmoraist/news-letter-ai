CREATE TABLE t_theme (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    customer_id UUID NOT NULL,

    CONSTRAINT pk_t_theme PRIMARY KEY (id),

    CONSTRAINT fk_theme_on_customer FOREIGN KEY (customer_id)
        REFERENCES t_customer (id) ON DELETE CASCADE
);

CREATE INDEX idx_theme_customer_id ON t_theme(customer_id);