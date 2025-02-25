CREATE TABLE customer (
    id              BIGSERIAL PRIMARY KEY,
    firstname       varchar(40) NOT NULL,
    lastname        varchar(40) NOT NULL,
    ssn             char(11) NOT NULL,
    income          numeric(10,2)
);

INSERT INTO customer (firstname, lastname, ssn, income) VALUES
('John', 'Doe', '123-45-6789', 50000.00),
('Jane', 'Smith', '987-65-4321', 75000.00),
('Alice', 'Johnson', '555-55-5555', 85000.00),
('Bob', 'Williams', '222-22-2222', 60000.00),
('Charlie', 'Brown', '333-33-3333', 40000.00);

CREATE TABLE realtor (
    id                  BIGSERIAL PRIMARY KEY,
    employeenum         numeric unique not null,
    firstname           varchar(40) NOT NULL,
    lastname            varchar(40) NOT NULL,
    commissionrate      NUMERIC(5,4) CHECK (commissionRate >= 0 AND commissionRate < 1)
);

INSERT INTO realtor (employeenum, firstname, lastname, commissionrate) VALUES
(1001, 'Tom', 'Taylor', 0.05),
(1002, 'Emma', 'Stone', 0.04),
(1003, 'Liam', 'Smith', 0.06),
(1004, 'Olivia', 'Johnson', 0.03),
(1005, 'Noah', 'Brown', 0.05);


CREATE TABLE home (
    id                  BIGSERIAL PRIMARY KEY,
    address             INTEGER CHECK (address BETWEEN 1 AND 999999) NOT NULL,
    street              varchar(40) not null,
    city                varchar(40) NOT NULL,
    state               char(2) NOT NULL,
    price               INTEGER CHECK (price BETWEEN 1 AND 9999999) NOT NULL,
    squarefeet          INTEGER CHECK (squarefeet > 0) not null
);


INSERT INTO home (address, street, city, state, price, squarefeet) VALUES
(1, 'Main St', 'Springfield', 'IL', 250000, 1500),
(2, 'Oak Ave', 'Metropolis', 'NY', 300000, 2000),
(3, 'Pine Rd', 'Gotham', 'NJ', 150000, 1200),
(4, 'Maple Dr', 'Star City', 'CA', 200000, 1800),
(5, 'Birch Blvd', 'Central City', 'TX', 350000, 2200);

CREATE TABLE purchase (
    id                      BIGSERIAL PRIMARY KEY,
    customerid              BIGINT REFERENCES customer(id) ON DELETE CASCADE not null,
    realtorid               BIGINT REFERENCES realtor(id) ON DELETE CASCADE not null,
    homeid                  BIGINT REFERENCES home(id) ON DELETE CASCADE not null,
    loan                    integer check (loan between 1 and 9999999) not null,
    downpayment             INTEGER CHECK (downpayment BETWEEN 1 AND 9999999) NOT NULL
);

INSERT INTO purchase (customerid, realtorid, homeid, loan, downpayment) VALUES
(1, 1, 1, 200000, 50000),
(2, 2, 2, 250000, 60000),
(3, 1, 3, 100000, 30000),
(1, 3, 4, 150000, 40000),
(4, 2, 5, 300000, 70000);