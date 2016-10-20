CREATE TABLE customers
(
  id serial NOT NULL,
  login text NOT NULL,
  password text NOT NULL,
  name text NOT NULL,
  address text,
  phone text,
  email text NOT NULL,
  credit_card text,
  CONSTRAINT login_name UNIQUE (login),
  CONSTRAINT unique_customer_id UNIQUE (id)
);
