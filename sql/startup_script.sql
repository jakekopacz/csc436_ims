create database if not exists Inventory;
use Inventory;

create table if not exists Customer (
    email varchar(32),
    credit_card varchar(16),
    primary key(email)
);

create table if not exists Supplier (
    email varchar(32),
    account_balance numeric(8,2),
        lead_time int,
    primary key(email)
);

create table if not exists Item (
    item_id int,
    quantity int,
    price numeric(7,2),
    category varchar(32),
    primary key(item_id)
);

create table if not exists SalesOrder (
    order_id int,
    shipping_option varchar(16),
    tracking_num varchar(32),
    customer_email varchar(32),
    primary key(order_id),
    foreign key(customer_email) references Customer(email)
);

create table if not exists ReplenishOrder (
        replenish_id int,
        shipping_option varchar(16),
    tracking_num varchar(32),
    supplier_email varchar(32),
    primary key(replenish_id),
    foreign key(supplier_email) references Supplier(email)
);

create table if not exists ItemOrder (
    order_id int,
    item_id int,
    quantity int,
    primary key(order_id, item_id),
    foreign key(item_id) references Item(item_id),
    foreign key(order_id) references SalesOrder(order_id)
);

create table if not exists ItemReplenish (
    replenish_id int,
    item_id int,
    quantity int,
    primary key(replenish_id, item_id),
    foreign key(item_id) references Item(item_id),
    foreign key(order_id) references SalesOrder(order_id)
);

create table if not exists ItemReplenish (
    replenish_id int,
    item_id int,
    quantity int,
    primary key(replenish_id, item_id),
    foreign key(item_id) references Item(item_id),
    foreign key(replenish_id) references ReplenishOrder(replenish_id)
);

create table if not exists SupplierList (
        cost INT,
        supplier_email varchar(32) references Supplier(email),
    item_id INT references Item(item_id),
    primary key(supplier_email, item_id)
);

create table if not exists MailingList(
    customer_email varchar(32),
    supplier_email varchar(32),
    address_1 varchar(128),
    address_2 varchar(128),
    address_3 varchar(128),
    city varchar(128),
        state char(2),
    country char(2),
    postal_code varchar(16),
    unique(customer_email,supplier_email),
    foreign key(customer_email) references Customer(email),
    foreign key(supplier_email) references Supplier(email),
    check(customer_email is null and supplier_email is not null or customer_email is not null and supplier_email is null)
);

create table if not exists PhoneList(
    customer_email varchar(32),
    supplier_email varchar(32),
    work_phone varchar(16),
    home_phone varchar(16),
    cell_phone varchar(16),
    unique(customer_email,supplier_email),
    foreign key(customer_email) references Customer(email),
    foreign key(supplier_email) references Supplier(email),
    check(customer_email is null and supplier_email is not null or customer_email is not null and supplier_email is null)
);

  
