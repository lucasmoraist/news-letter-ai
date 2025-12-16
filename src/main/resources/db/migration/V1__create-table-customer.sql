CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE t_customer (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(50),
    gender VARCHAR(50),
    is_active BOOLEAN DEFAULT TRUE
);