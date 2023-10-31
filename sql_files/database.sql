create database Inventory;
Use Inventory;


create table Customer (
    email varchar(32),
    credit_card varchar(16),
    primary key(email)
);

create table Supplier (
    email varchar(32),
    account_balance numeric(8,2),
	lead_time int,
    primary key(email)
);

create table Item (
    item_id int,
    quantity int,
    price numeric(7,2),
    category varchar(32),
    primary key(item_id)
);

#Many to many relationship between supplier and item.(i.e 1 item is supplied by 3 suppliers, 3 items are supplied by one supplier)
#cost is in this table bc different suppliers may have different cost.
Create table SupplierList (
	cost INT,
	supplier_email varchar(32) references Supplier(email),
    item_id INT references Item(item_id),
    primary key(supplier_email, item_id)
);

#Total attribute is redundent
create table SalesOrder (
    order_id int,
    total numeric(8,2),
    shipping_option varchar(16),
    tracking_num varchar(32),
    customer_email varchar(32),
    primary key(order_id),
    foreign key(customer_email) references Customer(email)
);

#Total attribute is redundent
create table ReplenishOrder (
	replenish_id int,
    total numeric(8,2),
	shipping_option varchar(16),
    tracking_num varchar(32),
    supplier_email varchar(32),
    primary key(replenish_id),
    foreign key(supplier_email) references Supplier(email)
);

#one order to many itemOrder
#one item to many itemOrder
create table ItemOrder (
    order_id int,
    item_id int,
    quantity int,
    primary key(order_id, item_id),
    foreign key(item_id) references Item(item_id),
    foreign key(order_id) references SalesOrder(order_id)
);

#same as above, this and ItemReplenish could be combined but I think it might complicate things
create table ItemReplenish (
    replenish_id int,
    item_id int,
    quantity int,
    primary key(replenish_id, item_id),
    foreign key(item_id) references Item(item_id),
    foreign key(replenish_id) references ReplenishOrder(replenish_id)
);

create table MailingList(
    customer_email varchar(32),
    supplier_email varchar(32),
    address_1 varchar(128),
    address_2 varchar(128),
    address_3 varchar(128),
    city varchar(128),
	state char(2), 
    country char(2),
    postal_code varchar(16),
    primary key(customer_email,supplier_email),
    foreign key(customer_email) references Customer(email),
    foreign key(supplier_email) references Supplier(email),
    check(customer_email is null or supplier_email is null and customer_email != supplier_email)
);

create table PhoneList(
    customer_email varchar(32),
    supplier_email varchar(32),
    work_phone varchar(16),
    home_phone varchar(16),
    cell_phone varchar(16),
    primary key(customer_email,supplier_email),
    foreign key(customer_email) references Customer(email),
    foreign key(supplier_email) references Supplier(email),
    check(customer_email is null or supplier_email is null and customer_email != supplier_email)
);